package bagoftasks.proto;

import java.rmi.*;

public interface Task {
    public boolean run();
    public Integer getId();
}
