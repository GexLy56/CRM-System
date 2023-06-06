package bitlab.spring.sprint2.controllers;

import bitlab.spring.sprint2.entity.ApplicationRequest;
import bitlab.spring.sprint2.repos.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping(value = "/")
    public String indexPage(Model model) {
        List<ApplicationRequest> requestRepositoryList = requestRepository.findAll();
        model.addAttribute("requests", requestRepositoryList);
        return "index";
    }

    @GetMapping(value = "/details/{id}")
    public String requestDetails(@PathVariable(name = "id") Long id, Model model) {
        ApplicationRequest request = requestRepository.findById(id).orElse(null);
        if (request != null) {
            model.addAttribute("request", request);
            return "details";
        }
        return null;
    }

    @GetMapping(value = "/add-request")
    public String addPage() {
        return "add";
    }

    @PostMapping(value = "/add-request")
    public String addRequest(@RequestParam(name = "userName") String userName,
                             @RequestParam(name = "courseName") String courseName,
                             @RequestParam(name = "phone") String phone,
                             @RequestParam(name = "commentary") String commentary) {
        ApplicationRequest request = new ApplicationRequest();
        request.setUserName(userName);
        request.setCourseName(courseName);
        request.setPhone(phone);
        request.setCommentary(commentary);
        request.setHandled(false);
        requestRepository.save(request);
        return "redirect:/";
    }

    @GetMapping(value = "/new-requests")
    public String newRequests(Model model) {
        List<ApplicationRequest> requestRepositoryList = requestRepository.findAllByHandled(false);
        model.addAttribute("requests", requestRepositoryList);
        return "new";
    }

    @GetMapping(value = "/handled-requests")
    public String handledRequests(Model model) {
        List<ApplicationRequest> requestRepositoryList = requestRepository.findAllByHandled(true);
        model.addAttribute("requests", requestRepositoryList);
        return "old";
    }

    @PostMapping(value = "/handle-request")
    public String handleRequest(ApplicationRequest request, Model model) {
        request.setHandled(true);
        requestRepository.save(request);
        model.addAttribute("request", request);
        return "details";
    }

    @PostMapping(value = "/delete-request")
    public String deleteRequest(@RequestParam(name = "id") Long id) {
        requestRepository.deleteById(id);
        return "redirect:/";
    }
}
