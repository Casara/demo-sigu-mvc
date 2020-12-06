package br.casara.sigu.web.pages;

import lombok.NoArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@NoArgsConstructor
public class CustomErrorController implements ErrorController {

  @Override
  public String getErrorPath() {
    return null;
  }

  @RequestMapping("/error")
  public String handleError(final HttpServletRequest request) {
    var errorPage = "error";
    final var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      final var statusCode = Integer.parseInt(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        errorPage = "error/404";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        errorPage = "error/500";
      }
    }

    return errorPage;
  }

}
