package com.tuongky.servlet.problem;

import com.tuongky.backend.ProblemAttemptDao;
import com.tuongky.backend.ProblemDao;
import com.tuongky.model.datastore.Problem;
import com.tuongky.model.datastore.ProblemAttempt;
import com.tuongky.util.JsonUtils;

import javax.servlet.http.HttpServlet;
import java.util.List;

/**
 * Created by sngo on 2/13/14.
 */
public class LastAttemptsServlet extends HttpServlet{
  private static final String PAGE_SIZE_FIELD = "pageSize";

  private static final String ROOT_KEY = "lastAttempts";

  private static int PAGE_SIZE_DEFAULT = 20;

  public void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
          throws javax.servlet.ServletException, java.io.IOException {
    String pageSize = req.getParameter(PAGE_SIZE_FIELD);

    int size = PAGE_SIZE_DEFAULT;

    if (pageSize != null && !pageSize.isEmpty()) {
      try {
        size = Integer.parseInt(pageSize);
      }
      catch (NumberFormatException e){
        resp.getWriter().println(JsonUtils.toJson(ROOT_KEY, "NumberFormatException"));
        return;
      }
    }

    List<ProblemAttempt> list = ProblemAttemptDao.instance.find(0, size);

    resp.getWriter().println(JsonUtils.toJson(ROOT_KEY, list));
  }

}
