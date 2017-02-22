package com.ironyard.controller.mvc;

import com.ironyard.data.MessageBoard;
import com.ironyard.repo.MessageBoardRepo;
import com.ironyard.services.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by wailm.yousif on 2/10/17.
 */

@Controller
@RequestMapping(path = "/secure/msgboards")
public class MessageBoardsController
{
    @Autowired
    MessageBoardRepo messageBoardRepo;

    private static final String[] sortByOptions = {"Creation Time (Desc)", "Name (Asc)"};

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
            Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "creationDate");
            sort = new Sort(order1);    //sort by time desc, user asc
        }
        else
        {
            Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "name");
            sort = new Sort(order1);    //sort by user asc, time desc
        }

        System.out.println("show msg boards");

        PageRequest pr = new PageRequest(page, pageSize, sort);
        Page<MessageBoard> listOfMsgBoards = messageBoardRepo.findAll(pr);

        dataModel.addAttribute("listOfMsgBoards", listOfMsgBoards);
        dataModel.addAttribute("pageSizeVal", pageSize);
        dataModel.addAttribute("sortByVal", sortBy);
        dataModel.addAttribute("pageSizeOpts", Utils.getPageSizeOptions(pageSize, 2, 6, 1));
        dataModel.addAttribute("sortByOpts", Utils.getSortByOptions(sortBy, sortByOptions));

        return "/secure/messageboards";
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, String msgBoardName,
                      @RequestParam(value = "page", required = false) Integer page,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                      @RequestParam(value = "sortBy", required = false) Integer sortBy
                      )
    {
        System.out.println("add msg boards");
        System.out.println("msgBoardName=" + msgBoardName + "#");

        MessageBoard messageBoard = new MessageBoard(msgBoardName, new Date());
        messageBoardRepo.save(messageBoard);

        if (page == null)
            page = 0;
        if (pageSize == null)
            pageSize = 2;
        if (sortBy == null)
            sortBy = 1;
        return "redirect:/secure/msgboards/show?page=" + page + "&pageSize=" + pageSize + "&sortBy=" + sortBy;
    }
}
