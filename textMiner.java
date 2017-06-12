import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

class textMiner {
    /*
    The create_subset method()
    Return type:void
    Input Parameters:
    1.Minimum Support Rate
    2.Map of unique products in the database
    3.List of all the transactions in the database
    4.Minimum confidence rate
    Job:
    According to Apriori principle the subsets of frequents
    item sets are also frequent.
    This method create subsets of the item sets and sends it
    to the check_min_support method to check
    the minimum support and stores the frequent item sets.It
    runs till the k-frequent item sets becomes one.
    Finally it calls generate_association_rule method which
    does the rest.
    */
    public static void create_subset(int n, Map<String, Integer>
            m1, ArrayList<String> data, int min_conf) {
        int n1 = 0, y = 0;
        Set<String> a3 = new HashSet<String>(m1.keySet());
        ArrayList<String> a1 = new ArrayList<String>(a3);
        ArrayList<ArrayList<String>> a2 = new
                ArrayList<ArrayList<String>>(); //Stores frequent item sets
        ArrayList<ArrayList<String>> a7 = new
                ArrayList<ArrayList<String>>(); //Stores subsets of item sets
        ArrayList<String> tempy = new ArrayList<String>();
        while (y == 0 || (a2.get(y).size()) > 1) //This loop runs till frequent item sets becomes
            one
        {
            if (y == 0) {
                ArrayList<String> s1 = new ArrayList<String>(m1.keySet());
                ArrayList<String> s2 = new ArrayList<String>(s1);
                ArrayList<String> s11 = new ArrayList<String>(s2);
                a7.add(new ArrayList<String>(s11));
                s2 = check_min_support(y, s1, n, data);
                a2.add(new ArrayList<String>(s2));
                s1 = new ArrayList<String>();
                for (int i = 0; i < s2.size(); i++) {
                    for (int j = i + 1; j < s2.size(); j++) {
                        s1.add(s2.get(i) + " " + s2.get(j));
                    }
                }
                a7.add(new ArrayList<String>(s1));
                a2.add(new
                        ArrayList<String>(check_min_support(y + 1, s1, n, data)));
            } else {
                ArrayList<String> s1 = new ArrayList<String>();
                ArrayList<String> ans1 = new ArrayList<String>();
                ArrayList<String> temp2 = new ArrayList<String>();
                ArrayList<String> tempy1 = new ArrayList<String>();
                s1 = new ArrayList<String>(a2.get(y));
                int b1 = 0;
                while (s1.size() > 0) {
                    int x1 = 0;
                    String m4 = s1.get(b1);
                    String m2[] = m4.split(" ");
                    StringBuilder sb = new StringBuilder("");
                    StringTokenizer st = new StringTokenizer("");
                    int x2 = 0;
                    while (x2 < y) {
                        if (x2 != 0)
                            sb.append(" ");
                        sb.append(m2[x2]);
                        x2++;
                    }
                    String tosearch = sb.toString();
                    int x3 = x2;
                    ArrayList<Integer> counts = new ArrayList<Integer>();
                    while (x1 < s1.size()) {
                        int flag = 1;
                        String my_check = s1.get(x1);
                        st = new StringTokenizer(tosearch);
                        while (st.hasMoreTokens()) {
                            if (flag == 0)
                                break;
                            String m3 = st.nextToken();
                            if (my_check.contains(m3))
                                flag = 1;
                            else
                                flag = 0;
                        }
                        if (flag == 1) {
                            x2 = x3;
                            String x4[] = my_check.split(" ");
                            st = new StringTokenizer(my_check);
                            sb = new StringBuilder("");
                            while (x2 < x4.length) {
                                sb.append(x4[x2]);
                                sb.append(" ");
                                x2++;
                            }
                            tempy1.add(sb.toString());
                            counts.add(x1);
                        }
                        x1++;
                    }
                    int hh = 0;
                    for (int yzz = 0; yzz < counts.size(); yzz++) {
                        s1.remove(counts.get(yzz) - hh);
                        hh++;
                    }
                    for (int i = 0; i < tempy1.size(); i++) {
                        for (int j = i + 1; j < tempy1.size(); j++) {
                            ans1.add(tosearch + " " + tempy1.get(i) + " " + tempy1.get(j));
                        }
                    }
                    tempy1.clear();
                    counts.clear();
                }
                a7.add(new ArrayList<String>(ans1));
                a2.add(new
                        ArrayList<String>(check_min_support(y + 1, ans1, n, data)));
            }
            y++;
        }
        generate_association_rule(a7, a2, data, min_conf);
    }

    /*
    The check_min_support method()
    Return type:ArrayList of frequent item sets that meets the
    minimum_support constraint
    Input parameters:
    1.Minimum Support
    2.ArrayList of subsets of item sets
    3.Minimum Confidence
    4.ArrayList of all the transaction in the database
    Job:
    This method checks the minimum support of the subsets of
    item sets and returns a ArrayList of frequent itemsets
    which meets the minimum support constraint.
    */
    public static ArrayList<String> check_min_support(int
                                                              x, ArrayList<String> s1, int min_conf, ArrayList<String>
                                                              data) {
        ArrayList<String> ans = new ArrayList<String>();
        if (x == 0) {
            int count = 0;
            StringTokenizer st = new StringTokenizer("");
            for (int i = 0; i < s1.size(); i++) {
                String xy = s1.get(i);
                for (int j = 0; j < data.size(); j++) {
                    st = new StringTokenizer(data.get(j));
                    while (st.hasMoreTokens()) {
                        if (xy.equals(st.nextToken()))
                            count++;
                    }
                }
                if (((count * 100 / data.size())) >= min_conf)
                    ans.add(xy);
                count = 0;
            }
        } else {
            for (int i = 0; i < s1.size(); i++) {
                ArrayList<String> temp1 = new ArrayList<String>();
                String tocheck = s1.get(i);
                int x1 = 0;
                while (x1 < data.size()) {
                    int flag = 1;
                    StringTokenizer st = new StringTokenizer(tocheck);
                    String m1 = data.get(x1);
                    while (st.hasMoreTokens()) {
                        if (flag == 1) {
                            String t1 = st.nextToken();
                            if (m1.contains(t1))
                                flag = 1;
                            else
                                flag = 0;
                        } else
                            break;
                    }
                    if (flag == 1)
                        temp1.add(m1);
                    x1++;
                }
                if ((temp1.size() * 100 / data.size()) >= min_conf)
                    ans.add(s1.get(i));
            }
        }
        return ans;
    }

    /*
    The check_confidence method
    Return type:double array
    Input Parameters:
    1.The association rule
    2.List of all transaction in the database
    3.Minimum Confidence Rate
    Job:
    It's job is to calculate the support of the elements in
    the association rule and the support of the elementsin the left hand side of the rule and send it to
    generate_association_rule.The values are stored in a
    double array.
    */
    public static double[] check_confidence(String
                                                    x, ArrayList<String> data, int min_confidence) {
        StringTokenizer st = new StringTokenizer(x);
        StringBuilder sb = new StringBuilder("");
        String check_1 = "";
        String check_2 = "";
        int x1 = 0;
        double c1 = 0, c2 = 0;
        ArrayList<String> temp1 = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            check_1 = st.nextToken();
            if (!check_1.equals("--->")) {
                sb.append(check_1);
                sb.append(" ");
            }
        }
        check_1 = sb.toString().trim();
        st = new StringTokenizer(x);
        sb = new StringBuilder("");
        while (st.hasMoreTokens()) {
            check_2 = st.nextToken();
            if (check_2.equals("--->"))
                break;
            else {
                sb.append(check_2);
                sb.append(" ");
            }
        }
        check_2 = sb.toString().trim();
        while (x1 < data.size()) {
            int flag = 1;
            st = new StringTokenizer(check_1);
            String m5 = data.get(x1);
            while (st.hasMoreTokens()) {
                if (flag == 1) {
                    String t1 = st.nextToken();
                    if (m5.contains(t1))
                        flag = 1;
                    else
                        flag = 0;
                } else
                    break;
            }
            if (flag == 1)
                temp1.add(m5);
            x1++;
        }
        c1 = (double) temp1.size() / data.size();
        x1 = 0;
        temp1 = new ArrayList<String>();
        while (x1 < data.size()) {
            int flag = 1;
            st = new StringTokenizer(check_2);
            String m5 = data.get(x1);
            while (st.hasMoreTokens()) {
                if (flag == 1) {
                    String t1 = st.nextToken();
                    if (m5.contains(t1))
                        flag = 1;
                    else
                        flag = 0;
                } else
                    break;
            }
            if (flag == 1)
                temp1.add(m5);
            x1++;
        }
        c2 = (double) temp1.size() / data.size();
        double[] ans = new double[2];
        ans[0] = c1 * 100;
        ans[1] = (c1 / c2) * 100;
        return ans;
    }

    /*
    The generate_association_rule method
    Return type:void
    Input Parameters:
    1.All the generated subsets
    2.Frequent item sets
    3.List of all transactions in the database
    4.Minimum confidence
    Job:
    It's job is to generate association rules of the k-
    frequent item sets starting from 2-frequent item sets and
    each
    generated rule is sent to check_confidence method to
    calculate it's confidence and the rules which meets the
    confidence
    are printed in the console.
    */
    public static void
    generate_association_rule(ArrayList<ArrayList<String>>
                                      all_itemsets, ArrayList<ArrayList<String>>
                                      frequent_itemsets, ArrayList<String> data, int min_conf) {
        int item_count = 1, k1 = 0, k2 = 0;
        System.out.println();
        for (ArrayList lo : frequent_itemsets) {
            if (all_itemsets.get(k2).size() != 0) {
                System.out.println();
                System.out.println("The total " + item_count + " item datasets are:");
                System.out.println();
                for (k1 = 0; k1 < all_itemsets.get(k2).size(); k1++) {
                    System.out.print("{" + all_itemsets.get(k2).get(k1).toString
                            ());
                    if (k1 + 1 != all_itemsets.get(k2).size())
                        System.out.print("}, ");
                    else
                        System.out.print("}");
                }
                k2++;
                System.out.println();
                System.out.println();
                if (frequent_itemsets.get(k2 - 1).size() != 0) {
                    System.out.println("The frequent " + item_count + " item datasets are:");
                    System.out.println();
                    for (int y = 0; y < lo.size(); y++) {
                        System.out.print("{" + lo.get(y).toString());
                        if (y + 1 != lo.size())
                            System.out.print("}, ");
                        else
                            System.out.print("}");
                    }
                    System.out.println();
                    item_count++;
                }
            }
        }
        System.out.println();
        String strong_rules = "";
        int level_counter = 0;
        System.out.println("The strong association rules are :");
        System.out.println();
        StringBuilder sb = new StringBuilder("");
        double xx[] = new double[2];
        for (ArrayList x : frequent_itemsets) {
            for (int i = 0; i < x.size(); i++) {
                int j = 0;
                String itemset = x.get(i).toString();
                StringTokenizer st = new StringTokenizer(itemset);
                if (st.countTokens() > 1) {
                    String[] items = new String[st.countTokens()];
                    int reverse_checker = items.length;
                    while (st.hasMoreTokens()) {
                        items[j] = st.nextToken();
                        j++;
                    }
                    for (int k = 0; k < items.length; k++) {
                        sb.append(items[k] + " ---> ");
                        for (int l = 0; l < items.length; l++) {
                            if (l != k) {
                                sb.append(items[l] + " ");
                            }
                        }
                        strong_rules = sb.toString();
                        xx = check_confidence(strong_rules, data, min_conf);
                        if (xx[1] >= min_conf)
                            System.out.println(strong_rules + " [" + xx[0] + ", " + xx[1] + "]");
                        sb = new StringBuilder("");
                        if (reverse_checker > 2) {
                            for (int l = 0; l < items.length; l++) {
                                if (l != k) {
                                    sb.append(items[l] + " ");
                                }
                            }
                            sb.append(" ---> " + items[k]);
                            strong_rules = sb.toString();
                            xx = check_confidence(strong_rules, data, min_conf);
                            if (xx[1] >= min_conf)
                                System.out.println(strong_rules + " [" + xx[0] + ", " + xx[1] + "]");
                            sb = new StringBuilder("");
                        }
                    }
                }
            }
        }
    }

    /*
    The appriori_apply method
    Return type:void
    Input parameters:
    1.Database name
    2.Minimum Support
    3.Minimum confidence
    Job:
    It's job is to establish database connectivity and store
    the transaction in a hash map and also identify
    unique products in the transaction and store it in a
    map.Finally it calls create_subset method.
    */
    public static void appriori_apply(int min_supp, int min_conf) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("final1.txt"));
        StringTokenizer st = new StringTokenizer("");
        String item = "";
        String temp1 = "";
        int value = 0;
        int num_of_items = 0;
        Map<String, Integer> m1 = new HashMap<String, Integer>();
        ArrayList<String> data = new ArrayList<String>();
        while ((temp1 = br.readLine()) != null) {
            String list = temp1;
            String te[] = list.split(" ");
            Arrays.sort(te);
            StringBuilder sb = new StringBuilder("");
            for (String x1 : te) {
                sb.append(x1);
                sb.append(" ");
            }
            data.add(sb.toString());
            st = new StringTokenizer(sb.toString());
            while (st.hasMoreTokens()) {
                item = st.nextToken();
                if (m1.containsKey(item)) {
                    value = m1.get(item);
                    value++;
                    m1.put(item, value);
                } else {
                    m1.put(item, value);
                }
            }
        }
        Map<String, Integer> m3 = new TreeMap<String, Integer>(m1);
        min_supp = (min_supp * 100) / data.size();
        create_subset(min_supp, m3, data, min_conf);
    }

    /*The proccessDtaset method
    Return type:void
    Job:
    It's job is to open the sgm file and ready the documents which is located between
    the <body> and </body> tags and pre process the document by removing unnecessary
    tags
    and then stores each document in an array list and then calls then datattoXml
    method*/
    public static void processDataset() throws Exception {
        ArrayList<String> Documents = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("reut-100.sgm"));
        String temp = "";
        while ((temp = br.readLine()) != null) {
            if (temp.contains("<body>")) {
                StringBuilder sb = new StringBuilder("");
                sb.append(temp);
                while ((temp = br.readLine()) != null && !(temp.contains("</body>"))) {
                    sb.append(temp);
                }
                sb.append(temp);
                String temp1 = sb.toString();
                temp1 = temp1.replace("</body>", "");
                temp1 = temp1.replace("<body>", "");
                temp1 = temp1.replace("</dateline><body>", "");
                StringTokenizer st = new StringTokenizer(temp1);
                temp1 = temp1.replaceAll("\\d", "");
                Documents.add(temp1);
            }
        }
        datatoXml(Documents);
    }

    /*The dtatatoXml method
    Return type:void
    Input parameters
    1.ArrayList of documents
    Job:
    This method creates a stanford nlp object and calls the xml print function in
    stanford nlp api
    for each of the documents in the arraylist which converts each document to xml of
    tokens.
    Each xml is stored as a file in the ou directory. After this each xml is read
    again
    and put in an arrayList called converted Docs.The reason for creating temporary
    output files
    is standford nlp xmlprint function accepts only streams as parameters.Finally it
    calls
    the xmlParser method with convertedDocs as parameter.*/
    public static void datatoXml(ArrayList<String> inputData) throws Exception {
        ArrayList<String> convertedDocs = new ArrayList<String>();
        Properties props = new Properties();
        props.put("annotators", "tokenize, cleanxml, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        for (int i = 0; i < inputData.size(); i++) {
            ByteArrayOutputStream xmlout = new ByteArrayOutputStream();
            Annotation annotation = new Annotation(inputData.get(i));
            pipeline.annotate(annotation);
            List<CoreLabel> tokens =
                    annotation.get(CoreAnnotations.TokensAnnotation.class);
            if (xmlout != null)
                pipeline.xmlPrint(annotation, xmlout);
            convertedDocs.add(new String(xmlout.toByteArray(), "UTF-8"));
        }
        int j = 0;
        for (String xl : convertedDocs) {
            PrintStream xx = new PrintStream("output/ou-" + j + ".xml");
            xx.print(xl);
            j++;
            xx.close();
        }
        xmlParser(convertedDocs);
    }

    /*The createStopList method
    Return type:ArrayList of strings
    Job:
    This method reads a stop list file and stores it in a Arraylist and return thearraylist which
    will be used later to remove stopwords */
    public static ArrayList<String> createStopList() throws Exception {
        ArrayList<String> stopWords = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("stopList.txt"));
        String stopword = "";
        while ((stopword = br.readLine()) != null)
            stopWords.add(stopword);
        return stopWords;
    }

    /*The processTokenList method
    Return type:ArrayList of string
    Input parameter:ArrayList of tokens
    Job:
    It's job is to remove all the punctuation marks from the token.
    */
    public static ArrayList<String> processTokenList(ArrayList<String> tokenList) {
        ArrayList<String> filteredTokens = new ArrayList<String>();
        for (String token : tokenList) {
            token = token.replaceAll("[&$-+.^:,'`\"<>/]", "");
            token = token.replaceAll("[!-~]?\\b[\\w]\\b[!-~]?", "");
            token = token.replaceAll("\\\\", "");
            filteredTokens.add(token + " ");
        }
        return filteredTokens;
    }

    /*The xmlParser method
    Return type:void
    Input parameter:An arraylist of xml documents generated by Stanford nlp parser
    Job:
    It's job is to use XPath to parse the xml and check for stop words and add all
    the valid tokens in each document to an Arraylist.
    It also uses Binary search for searching which reduces the search time to O(logn).
    It calls createstoplist to create the stop list and computetermfrequency method.
    */
    public static void xmlParser(ArrayList<String> xmlDocs) throws Exception {
        ArrayList<String> tokenList = new ArrayList<String>();
        ArrayList<String> stopWords = createStopList();
        for (int j = 0; j < xmlDocs.size(); j++) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
// parse the xml to extract term from lemma tags
            Document doc = builder.parse("output/ou-" + j + ".xml");
            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();
            XPathExpression expr = xpath.compile("//lemma/text()");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < nodes.getLength(); i++) {
                if (Collections.binarySearch(stopWords, nodes.item(i).getNodeValue()) < 0) {
                    sb.append(nodes.item(i).getNodeValue());
                    sb.append(" ");
                }
            }
            tokenList.add(sb.toString());
        }
        tokenList = processTokenList(tokenList);
        computeTermFrequency(tokenList);
    }

    /*The computeTermFrequency method
    Return type:void
    Input Parameters:An arraylist of tokens
    Job:
    It's job is to count number of times each token appears in each doucument and
    create an hashmap with tokens
    as keys and number of occurrence as values.It then calls tfidf method.
    */
    public static void computeTermFrequency(ArrayList<String> tokenList) throws
            Exception {
        Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
        ArrayList<Map> maplist = new ArrayList<Map>();
        for (String document : tokenList) {
            frequencyMap = new HashMap<String, Integer>();
            StringTokenizer st = new StringTokenizer(document);
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (frequencyMap.containsKey(token))
                    frequencyMap.put(token, frequencyMap.get(token) + 1);
                else
                    frequencyMap.put(token, 1);
            }
            maplist.add(frequencyMap);
        }
        int u = 1;
        String newLine = System.getProperty("line.separator");
        for (Map m : maplist) {
            System.out.println(newLine + "**********************************Term frequency of
                    each token in Document number"+u+":*******************************"+newLine);
            Iterator it = m.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                System.out.print(pairs.getKey() + " = " + pairs.getValue() + " ");
//it.remove();
            }
            System.out.println();
            u++;
        }
        System.out.print(newLine);
        tfidf(maplist);
    }

    /*The tfidf method
    Return type:void
    Input parameters:
    1.ArrayList of term frequency hash maps
    Job:
    It's job is to compute the term frequency * inverse document frequency of each
    term
    in the documents and store it in a arraylist of hash maps and call
    frequentWordFinder method */
    public static void tfidf(ArrayList<Map> termFrequency) throws Exception {
        ArrayList<Map> temp = new ArrayList<Map>(termFrequency);
        ArrayList<Map> idf = new ArrayList<Map>();
        for (int i = 0; i < temp.size(); i++) {
            Map<Double, String> m1 = new HashMap<Double, String>();
            Map<String, Integer> x2 = new HashMap<String, Integer>(temp.get(i));
            for (Map.Entry<String, Integer> entry : x2.entrySet()) {
                String token = entry.getKey();
                double x1 = 1.0;
                for (int j = 0; j < temp.size(); j++) {
                    if (i != j) {
                        if (temp.get(j).containsKey(token))
                            x1++;
                    }
                }
                m1.put(new Double(new
                        BigDecimal(entry.getValue() * Math.log10(temp.size() / x1)).setScale(6, BigDecimal.ROUN
                        D_HALF_UP).doubleValue()), token);
            }
            idf.add(m1);
        }
        int k = 1;
        String newLine = System.getProperty("line.separator");
        for (Map m : idf) {
            System.out.println(newLine + "****************************The (term frequency *
                    inverse document frequency) of each token in document number
            "+k+":***********************************"+newLine);
            k++;
            Iterator it = m.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                System.out.print(pairs.getValue() + " = " + pairs.getKey() + " ");
            }
            System.out.println();
        }
        frequentWordFinder(idf);
    }

    /*The frequentWordFinder method
    Return type:voidInput parameters:ArrayList of tfidf hash maps
    Job:
    It's job is to sort the tokens in the document in a decreasing order based on
    tfidf values.
    The sorting algorithm used is java built in Tim sort which is a combination of
    merge
    and insertion sort.It's worst time complexity is O(nlogn).It limits the number of
    keywords to 7.Then it writes the frequent tokens in a text file called final1.txt
    and calls
    the callApriori method
    */
    public static void frequentWordFinder(ArrayList<Map> tfifList) throws Exception {
        ArrayList<Double> sorted = new ArrayList<Double>();
        ArrayList<String> sortedList = new ArrayList<String>();
        for (int k = 0; k < tfifList.size(); k++) {
            sorted = new ArrayList<Double>();
            Map<Double, String> p1 = new HashMap<Double, String>(tfifList.get(k));
//System.out.println(p1.get(1.79));
            for (Map.Entry<Double, String> entry : p1.entrySet()) {
                Double t1 = entry.getKey();
                sorted.add(t1);
            }
            Collections.sort(sorted, Collections.reverseOrder());
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < 7; i++) {
                if (i < sorted.size()) {
                    sb.append(p1.get(sorted.get(i)));
                    sb.append(" ");
                }
            }
            sortedList.add(sb.toString());
        }
        PrintWriter o = new PrintWriter("final1.txt");
        int documentId = 1;
        for (String n : sortedList) {
            o.println(n);
            documentId++;
        }
        o.close();
        callApriori();
    }

    /*The callApriori method:
    Return type:void
    Job:
    It's job is to accept user input for minimum support and
    minimum confidence then calls the appriori_apply method with
    these parameters.*/
    public static void callApriori() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int min_supp = 0, min_conf = 0;
        System.out.println();
        System.out.println("Enter the minimum support rate");
        min_supp = Integer.parseInt(br.readLine());
        System.out.println("Enter the minimum confidence rate");
        min_conf = Integer.parseInt(br.readLine());
        appriori_apply(min_supp, min_conf);
    }

    public static void main(String args[]) throws Exception {
        processDataset();
    }
}
