package bagoftasks.proto;

import java.io.Serializable;
import javax.sql.rowset.CachedRowSet;

public class QueryResult implements Serializable {
    private CachedRowSet resultSet;
    private Integer affectedRows;

    public QueryResult(CachedRowSet resultSet) {
        this.resultSet = resultSet;
        this.affectedRows = null;
    }

    public QueryResult(int affectedRows) {
        this.resultSet = null;
        this.affectedRows = affectedRows;
    }

    public boolean isSelectResult() {
        return resultSet != null;
    }

    public CachedRowSet getResultSet() {
        return resultSet;
    }

    public Integer getAffectedRows() {
        return affectedRows;
    }
}