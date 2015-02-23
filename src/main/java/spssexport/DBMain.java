package spssexport;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DBMain {

    public static void main(String args[]) {
        new DBMain();
    }

    TreeSet<String> parameterList;
    List<Queries> queryResult;
    HashMap<String, HashMap> patients;

    public DBMain() {

        //get jdbcTemplatePersonDAO
        QueriesDAO queriesDAO = new QueriesDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        
        //select all
        queryResult = queriesDAO.selectAll();

        // creating parameter list
        createParameterList();

        createPatientList();

        //String csv = createCSV();
        //System.out.print("csv:\n" + csv);
    }

    /**
     * <em>Method for creating the parameter list.</em><br/> The method obtains
     * all the parameter names from the query, it works as a sortedSet in a way,
     * that every parameter name can exist just once, and the method sorts the
     * names alphabetically.
     * <br/>
     * Parameter name consist of the original paramter name PLUS week number
     *
     * Fills parameterList
     *
     * @param queryResult
     */
    private void createParameterList() {
        parameterList = new TreeSet<String>();

        // iterate over list, obtaining parameters, filling parameterList
        for (Queries q : queryResult) {
            //System.out.println(q.getVariablename());
            //System.out.println(q.getWeek());            
            String paramName = createParamName(q.getVariablename(), q.getWeek());
            parameterList.add(paramName);
        }

//        for(String s: parameterList){
//            System.out.println(s);
//        }
    }

    /**
     * Creates parameterName from original parameter name and week of the
     * examination
     *
     * @param oParamName
     * @param week
     * @return
     */
    private String createParamName(String oParamName, int week) {
        return oParamName + " # " + week;
    }

    public String createCSV() {
        StringBuilder sb = new StringBuilder();

        char inLinedelimiter = ';';
        char lineDelimiter = '\n';

        // caption line
        sb.append("páciens neve" + inLinedelimiter);
        for (String s : parameterList) {
            sb.append(s + inLinedelimiter);
        }
        sb.append(lineDelimiter);

        // other lines
        for (String patient : patients.keySet()) {
            sb.append(patient);
            sb.append(inLinedelimiter);

            // for every parameter
            for (String param : parameterList) {
                // patient's param - value Map
                HashMap<String, String> map = patients.get(patient);
                if (map.containsKey(param)) {
                    sb.append(map.get(param));
                }
                sb.append(inLinedelimiter);
            }
            sb.append(lineDelimiter);
        }
        return sb.toString();
    }

    private void createPatientList() {
        patients = new HashMap<String, HashMap>();

        // create patient list
        for (Queries q : queryResult) {
            // if patient entry is not in list we add it
            if (!patients.containsKey(q.getPatientname())) {
                patients.put(q.getPatientname(), new HashMap<String, String>());
            }

            // we add query line to patient's parameter-value map
            HashMap<String, String> map = patients.get(q.getPatientname());
            map.put(createParamName(q.getVariablename(), q.getWeek()), q.getValue());
        }

        // check patient list
        //printPatientList();
    }

    private void printPatientList() {
        for (String key : patients.keySet()) {
            System.out.print("\npatients:\t");
            System.out.println(key);

            // list parameter-value pairs belonging to the patient
            HashMap<String, String> map = patients.get(key);

            for (String paramKey : map.keySet()) {
                System.out.println(paramKey + ":\t" + map.get(paramKey));
            }
        }
        System.out.println("\n\n");
    }

}
