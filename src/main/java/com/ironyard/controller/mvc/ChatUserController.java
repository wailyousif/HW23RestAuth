package com.ironyard.controller.mvc;

import com.ironyard.data.ChatUser;
import com.ironyard.data.UserPermission;
import com.ironyard.repo.ChatUserRepo;
import com.ironyard.repo.UserPermissionRepo;
import com.ironyard.services.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by wailm.yousif on 2/12/17.
 */

@Controller
@RequestMapping(path = "/secure/chatusers")
public class ChatUserController
{
    @Autowired
    ChatUserRepo chatUserRepo;

    @Autowired
    UserPermissionRepo userPermissionRepo;

    @Value("${upload.location}")
    private String uploadLocation;

    private static final int MIN_PHOTO_WIDTH = 100;
    private static final int MIN_PHOTO_HEIGHT = 100;

    private static final String[] sortByOptions = {"Username (Asc)", "Display Name (Asc)"};


    @RequestMapping(path = "/show", method = RequestMethod.GET)
    public String show(Model dataModel,
                       @RequestParam(value = "page", required = false) Integer page,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestParam(value = "sortBy", required = false) Integer sortBy
                       )
    {
        if (page == null) {
            System.out.println("page = null");
            page = 0;
        }
        if (pageSize == null) {
            System.out.println("pageSize = null");
            pageSize = 2;
        }
        if (sortBy == null) {
            System.out.println("sortBy = null");
            sortBy = 1;
        }

        Sort sort;
        if (sortBy == 1)
        {
            Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "username");
            sort = new Sort(order1);    //sort by time desc, user asc
        }
        else
        {
            Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "displayName");
            sort = new Sort(order1);    //sort by user asc, time desc
        }

        System.out.println("show users");

        PageRequest pr = new PageRequest(page, pageSize, sort);
        Page<ChatUser> listOfUsers = chatUserRepo.findAll(pr);

        dataModel.addAttribute("listOfUsers", listOfUsers);
        dataModel.addAttribute("pageSizeVal", pageSize);
        dataModel.addAttribute("sortByVal", sortBy);
        dataModel.addAttribute("pageSizeOpts", Utils.getPageSizeOptions(pageSize, 2, 6, 1));
        dataModel.addAttribute("sortByOpts", Utils.getSortByOptions(sortBy, sortByOptions));

        return "/secure/chatusers";
    }



    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public String add(Model dataModel, String userName, String passWord,
                      String displayName, Integer createUserPerm,
                      Integer createMsgBoardPerm, Integer postMsgPerm,
                      @RequestParam(value = "userPhoto", required = false) MultipartFile userPhoto,
                      @RequestParam(value = "page", required = false) Integer page,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                      @RequestParam(value = "sortBy", required = false) Integer sortBy
                      ) throws IOException
    {

        System.out.println("add users");
        System.out.println(userName + passWord + displayName + createUserPerm +
        createMsgBoardPerm + postMsgPerm);

        UserPermission permission = new UserPermission(
                (createUserPerm == 1) ? true : false,
                (createMsgBoardPerm == 1) ? true : false,
                (postMsgPerm == 1) ? true : false
        );

        //userPermissionRepo.save(permission);
        ChatUser chatUser = new ChatUser(userName, passWord, displayName, permission);
        chatUserRepo.save(chatUser);

        //Avoid filling the disk with images if the user already exists
        if (chatUser.getId() != 0 && userPhoto != null)
        {
            String uploadedFileName = userName + "_" + userPhoto.getOriginalFilename();
            Files.copy(userPhoto.getInputStream(), Paths.get(uploadLocation + uploadedFileName));


            String minimizedFileName = Utils.getFileNameWithoutExtension(uploadedFileName) + ".min.jpg";

                    BufferedImage originalImg = ImageIO.read(userPhoto.getInputStream());
            int imgType = originalImg.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImg.getType();

            System.out.println("jpg image type = " + String.valueOf(imgType));

            BufferedImage minimizedJpg = resizeImage(originalImg, 5);
            ImageIO.write(minimizedJpg, "jpg",
                    new File(uploadLocation + minimizedFileName));


            chatUser.setPhotoFile(uploadedFileName);
            chatUser.setSmallPhotoFile(minimizedFileName);
            chatUserRepo.save(chatUser);
        }

        if (page == null)
            page = 0;
        if (pageSize == null)
            pageSize = 2;
        if (sortBy == null)
            sortBy = 1;
        return "redirect:/secure/chatusers/show?page=" + page + "&pageSize=" + pageSize + "&sortBy=" + sortBy;
    }


    private static BufferedImage resizeImage(BufferedImage originalImg, int imgType)
    {
        BufferedImage resizedImg = new BufferedImage(MIN_PHOTO_WIDTH, MIN_PHOTO_HEIGHT, imgType);
        Graphics2D g = resizedImg.createGraphics();
        g.drawImage(originalImg, 0, 0, MIN_PHOTO_WIDTH, MIN_PHOTO_HEIGHT, null);
        g.dispose();
        return resizedImg;
    }
}
