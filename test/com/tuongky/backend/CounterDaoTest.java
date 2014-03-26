package com.tuongky.backend;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.tuongky.model.UserRole;
import com.tuongky.model.datastore.User;
import com.tuongky.service.UpdateService;

/**
 * Created by sngo on 2/26/14.
 */
public class CounterDaoTest {

  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
          .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

  private static final UpdateService updateService = DatastoreUpdateService.instance;

  @Before
  public void init() {
    helper.setUp();
  }

  @Test
  public void testProblem() {
    String fen = "fen";
    String title = "title";
    String desc = "desc";
    String requirement = "req";

    long problemId_1 = ProblemDao.instance.create(fen, title, requirement, desc, null);
    long problemId_2 = ProblemDao.instance.create(fen, title, requirement, desc, null);

    assertEquals(2, CounterDao.getProblemsCount());
    assertEquals(1, problemId_2 - problemId_1);

    ProblemDao.instance.delete(problemId_1);
    assertEquals(1, CounterDao.getProblemsCount());
  }

  @Test
  public void testUser() {
    assertEquals(0, CounterDao.getUsersCount());
    updateService.saveFacebookUser("fb", "fb", null, UserRole.ADMIN);
    updateService.saveFacebookUser("fb", "fb", null, UserRole.USER);
    assertEquals(1, CounterDao.getUsersCount());
    updateService.saveFacebookUser("fb2", "fb2", null, UserRole.USER).getId();
    assertEquals(2, CounterDao.getUsersCount());
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }
}
