package spssexport;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class QueriesDAO {

    private SqlSessionFactory sqlSessionFactory = null;

    public QueriesDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<Queries> selectAll() {
        List<Queries> list = null;
        
        SqlSession session = sqlSessionFactory.openSession();
        
        try {
            list = session.selectList("Queries.selectAll");
        } finally {
            session.close();
        }
        //System.out.println("selectAll() --> " + list);
        return list;

    }

}
