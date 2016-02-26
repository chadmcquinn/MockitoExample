package com.inin.mumbai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    // if the class we are using can be instantiated
    // easily at runtime, then it is OK to do so.
    @Test
    public void testGetVersion() throws Exception {

        Repository r = new Repository(new MemoryDb());

        assertEquals("1.0.0", r.getVersion());
    }


    // Another approach is to create a Database subclass that is just for
    // testing (even an anonymous class). Making a new subclass isn't too hard
    // because the Database interface is so simple. But if it had many methods,
    // we wouldn't want to implement all of them just to test one method. So instead
    // we can use Mockito like this:
    @Mock
    private Database d;

    @Test
    public void testGetVersionMocks() throws Exception {
        // Create a Repository with our mocked Database
        Repository r = new Repository(d);

        // this tells Mockito what to do when getValueForKey() is called on 'd'
        when(d.getValueForKey("Version")).thenReturn("1.0.0");

        assertEquals("1.0.0", r.getVersion());
    }


    // We can do even better though. Notice in Repository() that
    // the version is set. We can store that and return it later.

    @Test
    public void testSetGetVersionMocks() throws Exception {
        // Using a one-element String array is the way to get around
        // closures requiring final variables. Even though we
        // make 'savedVersion' final, we can still assign to 'savedVersion[0]'.
        String[] savedVersion = new String[1];

        // By default, Mockito does nothing (or returns null) for a method if you
        // don't tell it to do anything else. This line tells Mockito to save
        // away the value that is passed when setValueForKey() is called.
        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                // this stores the second argument that setValueForKey() is called with
                savedVersion[0] = (String) (invocation.getArguments()[1]);
                return savedVersion[0];
            }
        }).when(d).setValueForKey(eq("Version"), anyString());

        // create the repository with our mocked Database
        Repository r = new Repository(d);

        when(d.getValueForKey("Version")).thenReturn(savedVersion[0]);

        assertEquals("1.0.0", r.getVersion());
    }

    // Finally, note that the anonymous Answer subclass we created is implementing a functional interface
    // (it does NOT have to be annotated that way). So we can use a lambda

    @Test
    public void testSetGetVersionMocksLambda() throws Exception {
        // Using a one-element String array is the way to get around
        // closures requiring final variables. Even though we
        // make 'savedVersion' final, we can still assign to 'savedVersion[0]'.
        String[] savedVersion = new String[1];

        // By default, Mockito does nothing (or returns null) for a method if you
        // don't tell it to do anything else. This line tells Mockito to save
        // away the value that is passed when setValueForKey() is called.
        doAnswer(invocation ->
            savedVersion[0] = (String) (invocation.getArguments()[1])
        ).when(d).setValueForKey(eq("Version"), anyString());

        // create the repository with our mocked Database
        Repository r = new Repository(d);

        when(d.getValueForKey("Version")).thenReturn(savedVersion[0]);

        assertEquals("1.0.0", r.getVersion());
    }


}