package ru.bellintegrator.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.bellintegrator.dto.MessageView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Component
public class AddMessagesResponseInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MessageService messageService;

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        Principal principal = request.getUserPrincipal();
        if(principal != null) {
            UserView userView = (UserView) ((Authentication) principal).getPrincipal();

            List<MessageView> accessMessagesToCurrentUser = messageService.getAccessMessagesTo(userView);
            if(!(accessMessagesToCurrentUser == null || accessMessagesToCurrentUser.isEmpty())) {
                modelAndView.getModelMap().addAttribute("accessMessagesToCurrentUser", accessMessagesToCurrentUser);
            }

            List<MessageView> infoMessagesToCurrentUser = messageService.getInfoMessagesTo(userView);
            if(!(infoMessagesToCurrentUser == null || infoMessagesToCurrentUser.isEmpty())) {
                modelAndView.getModelMap().addAttribute("infoMessagesToCurrentUser", infoMessagesToCurrentUser);
            }
        }
    }
}
