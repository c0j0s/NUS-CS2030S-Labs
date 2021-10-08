import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Roster rs = new Roster("AY2122");

        Scanner sc = new Scanner(System.in);
        int record = sc.nextInt();

        for (int i = 0; i < record; i++) {
            String name = sc.next();
            String module = sc.next();
            String assm = sc.next();
            String grade = sc.next();

            Assessment as = new Assessment(assm, grade);
            Student s = rs.get(name);
            if (s != null) {
                Module m = s.get(module);
                if (m != null) {
                    m.put(as);
                } else {
                    s.put(new Module(module).put(as));
                }
            } else {
                rs.put(new Student(name).put(new Module(module).put(as)));
            }
        }

        List<String> results = new ArrayList<String>();
        while (sc.hasNext()) {
            results.add(rs.getGrade(sc.next(), sc.next(), sc.next()));
        }

        for (String s : results) {
            System.out.println(s);
        }
        sc.close();
    }
}
