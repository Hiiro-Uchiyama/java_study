import java.io.*;
import java.util.*;
import java.text.*;

public class CalendarSystem{

    public class Schedule {
        public int sn;
        public Date date;
        public String content;
        public Person person;
        public Schedule(int s, String dt, String c, Person p) throws ParseException{
            sn=s;
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm");
            date=sdf.parse(dt);
            content=c;
            person=p;
        }
        public String toString(){
            if(person==null){
                return String.format("%04d:%tY/%<tm/%<td %<tH:%<tM %s", sn, date, content);
            }
            return String.format("%04d:%tY/%<tm/%<td %<tH:%<tM %s %s", sn, date, content, person);
        }
    }

    public class Person {
        public int sn;
        public String name;
        public Date birth;
        public char gender;
        //初期化
        public Person(int s, String n, String b, char g) throws ParseException {
            sn=s;
            name=n;
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
            birth=sdf.parse(b);
            gender=g;
        }
        //文字列化
        public String toString(){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            int td=Integer.parseInt(sdf.format(new Date()));
            int bd=Integer.parseInt(sdf.format(birth));
            int age=(td-bd)/10000;
            String g=(gender=='m')?"男":((gender=='f')?"女":"?");
            return name+"("+age+"歳:"+g+")";
        }
    }

    private int last_sn;
    private List<Schedule> sc_list;
    private int last_sn_p;
    private List<Person> ps_list;
    private String file_name="schedule.csv";
    private String[] load_command = { "load" };
    private String[] save_command = { "save" };

    public static void main(String[] args){
        (new CalendarSystem()).exec();
    }

    private void exec() {
        initSystem();
        boolean end_flag=false;
        loadSchedule(load_command);
        while(!end_flag){
            String[] cmd=inputCommand();
            switch(cmd[0]){
                case "quit": end_flag=true;
                    break;
                case "add": addSchedule(cmd);
                    break;
                case "list": listSchedules(cmd);
                    break;
                case "save": saveSchedules(cmd);
                    break;
                case "load": loadSchedule(cmd);
                    break;
                case "modify": modifySchedule(cmd);
                    break;
                case "del": deleteSchedule(cmd);
                    break;
                case "set": changeSettings(cmd);
                    break;
                case "addp": addPerson(cmd);
                    break;
                case "listp": listPersons(cmd);
                    break;
                case "help": showHelp();
                    break;
                default:
                    msg("コマンド"+cmd+"がわかりません。");
            }
        }
        saveSchedules(save_command);
        quitSystem();
    }

    // changeSettings
    private void changeSettings(String[] cmd){
        switch(cmd[1]){
            case "fn": setFileName(cmd[2]); break;
        }
    }

    // setFileName
    private void setFileName(String fn){
        file_name=fn;
        msg("保存ファイル名を"+fn+"に変更しました。");
    }

    private void initSystem() {
        msg("*** 予定管理システムSP3へようこそ ***");
        last_sn=0;
        sc_list=new ArrayList<Schedule>();
        ps_list=new ArrayList<Person>();
    }

    private void quitSystem() {
        msg("また使ってね");
    }

    private void msg(String m) {
        System.out.println(m);
    }

    private void msg0(String m) {
        System.out.print(m);
    }

    // input
    private String input(){
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        }catch(IOException e){}
        return null;
    }

    private String[] inputCommand() {
        msg0("コマンドは？");
        String s=input();
        if(s!=null){
            return s.split(" ");
        }
        String[] r={ "" };
        return r;
    }

    private void addSchedule(String[] cmd){
        if(cmd.length!=4 && cmd.length!=5){
            msg("addコマンドの構文が不正です");
            return;
        }
        try{
            String d=cmd[1]+" "+cmd[2];
            String c=cmd[3];
            Person p=null;
            if(cmd.length==5){
                p=findPerson(cmd[4]);
            } 
            sc_list.add(new Schedule(++last_sn,d,c,p));
        }catch(ParseException e){
            msg("日付が不正です");
        }
    }

    private Person findPerson(String n){
        for(Person p:ps_list){
            if(p.name.equals(n)){
                return p;
            }
        }
        return null;
    }

    private Person findPerson(int sn){
        for(Person p:ps_list){
            if(p.sn==sn){
                return p;
            }
        }
        return null;
    }

    private void listSchedules(String[] cmd){
        if(cmd.length==1){
            listAllSchedules();
            return;
        }
        switch(cmd[1]){
            case "d": listBitweenDates(cmd[2], cmd[3]); break;
            case "dt": listBitweenDateTimes(cmd[2]+" "+cmd[3], cmd[4]+" "+cmd[5]); break;
            case "k": listContainKeyword(cmd[2]); break;
        }
    }

    private void listAllSchedules(){
        for(Schedule s:sc_list){
            msg(""+s);
        }
    }

    private void listContainKeyword(String kw){
        System.out.println(kw);
        for(Schedule s:sc_list){
            if(s.content.indexOf(kw)!=-1){
                msg(" "+s);
            }
        }
    }

    private void listBitweenDates(String s1, String s2){
        listBitweenDateTimes(s1+" 00:00",s2+" 23:59");
    }

    private void listBitweenDateTimes(String s1, String s2){
        try{
            if(s1.charAt(0)=='-') s1="1900/1/1 00:00";
            if(s2.charAt(0)=='-') s2="9999/12/31 23:59";
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm");
            Date d1=sdf.parse(s1);
            Date d2=sdf.parse(s2);
            if(d1.compareTo(d2)>0){
                Date t=d1; d1=d2; d2=t;
            }
            for(Schedule s:sc_list){
                if(s.date.compareTo(d1)>=0&&s.date.compareTo(d2)<=0){
                    msg(" "+s);
                }
            }
        }catch(ParseException e){
            msg("日付が不正です。");
        }
    }

    // saveSchedules
    private void saveSchedules(String[] cmd){
        String fn=file_name;
        if(cmd!=null && cmd.length==2){
            fn=cmd[1];
        }
        try{
            PrintWriter pw=new PrintWriter(new FileWriter(fn));
            pw.println(ps_list.size());
            for(Person p:ps_list){
                String csv=String.format("%d,%s,%tY/%<tm/%<tm,%c", p.sn,p.name,p.birth,p.gender);
                pw.println(csv);
            }
            pw.println(sc_list.size());
            for(Schedule s:sc_list){
                int psn=-1;
                if(s.person!=null){
                    psn=s.person.sn;
                }
                String csv=String.format("%d,%tY/%<tm/%<td,%<tH:%<tM,%s,%d", s.sn,s.date,s.content,psn);
                pw.println(csv);
            }
            pw.close();
        }catch(IOException e){
            msg("保存失敗:"+fn);
        }
    }

    // loadSchedule
    private void loadSchedule(String[] cmd){
        String fn=file_name;
        if(cmd.length==2){
            fn=cmd[1];
        }
        try{
            BufferedReader br=new BufferedReader(new FileReader(fn));

            ps_list.clear();
            int n=Integer.parseInt(br.readLine());
            for(int i=0;i<n;i++){
                String l=br.readLine();
                String[] tok=l.split(",");
                int sn=Integer.parseInt(tok[0]);
                String nm=tok[1];
                String b=tok[2];
                char g=tok[3].charAt(0);
                ps_list.add(new Person(sn,nm,b,g));
                last_sn_p=sn;
            }

            sc_list.clear();
            n=Integer.parseInt(br.readLine());
            for(int i=0;i<n;i++){
                String l=br.readLine();
                String[] tok=l.split(",");
                int sn=Integer.parseInt(tok[0]);
                String d=tok[1]+" "+tok[2];
                String c=tok[3];
                int ps=Integer.parseInt(tok[4]);
                Person p=null;
                if(ps!=-1) p=findPerson(ps);
                sc_list.add(new Schedule(sn, d, c, p));
                last_sn=sn;
            }
            br.close();
        }catch(IOException e){
            msg("読み込み失敗:"+fn);
        }catch(ParseException e){
            msg("CSVファイルが不正です");
        }
    }

    // findSchedule
    private Schedule findScheduleBySn(int sn){
        for(Schedule s:sc_list){
            if(s.sn==sn){
                return s;
            }
        }
        return null;
    }

    // Date
    private void modifyScheduleDate(Schedule sc, String dt){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm");
            sc.date=sdf.parse(dt);
        }catch(ParseException e){
            msg("日付が不正です");
        }
    }

    // Content
    private void modifyScheduleContent(Schedule sc, String c){
        sc.content=c;
    }

    // Person name
    private void modifySchedulePerson(Schedule sc, String p){
        Person person=findPerson(p);
        sc.person=person;
    }

    // modifySchedule
    private void modifySchedule(String[] cmd){
        int sn=Integer.parseInt(cmd[1]);
        Schedule sc=findScheduleBySn(sn);
        if(sc==null){
            msg("予定がありません");
            return;
        }
        switch(cmd[2]){
            case "d": modifyScheduleDate(sc,cmd[3]+" "+cmd[4]);
            case "c": modifyScheduleContent(sc,cmd[3]); break;
            case "p": modifySchedulePerson(sc, cmd[3]);
        }
    }

    // dell
    private void deleteSchedule(String[] cmd){
        int sn=Integer.parseInt(cmd[1]);
        Schedule sc=findScheduleBySn(sn);
        if(sc==null){
            msg("予定がありません");
            return;
        }
        msg0("予定「"+sc+"」を削除しますか?");
        String s=input();
        // エラー箇所
        // もし何も押さずにエンターキーでスキップしようとするとエラーが発生する
        // Exception in thread "main" java.lang.StringIndexOutOfBoundsException: String index out of range: 0
	    // at java.base/java.lang.StringLatin1.charAt(StringLatin1.java:48)
	    // at java.base/java.lang.String.charAt(String.java:1512)
	    // at CalendarSystem.deleteSchedule(CalendarSystem.java:215)
	    // at CalendarSystem.exec(CalendarSystem.java:50)
	    // at CalendarSystem.main(CalendarSystem.java:29)
        if(s.charAt(0)=='y'){
            sc_list.remove(sc);
        }
    }

    private void showHelp() {
        msg("コマンド:");
        msg("  add    : 新しい予定を追加する（add 2020/5/1 8:00 出勤する）");
        msg("  list   : 予定の一覧を表示する");
        msg("  modify : 予定を修正する");
        msg("  del    : 予定を削除する");
        msg("  save   : 予定をファイルに書き出す");
        msg("  load   : 予定をファイルから読み込む");
        msg("  help   : コマンドの使用法を表示する");
        msg("  set    : 設定を変更する");
        msg("  quit   : 終了する");
    }

    private void addPerson(String[] cmd){
        if(cmd.length!=4){
            msg("コマンド構文が不正です。");
            return;
        }
        try{
            String n=cmd[1];
            String b=cmd[2];
            char m=cmd[3].charAt(0);
            ps_list.add(new Person(++last_sn_p, n, b, m));
        }catch(ParseException e){
            msg("日付が不正です。");
        }
    }

    private void listPersons(String[] cmd){
        for(Person p:ps_list){
            msg(" "+p);
        }
    }

    // modifySchedule person
    private void modifyPerson(String[] cmd){
        ;
    }

    // dell person
    private void deletePerson(String[] cmd){
        int ps=Integer.parseInt(cmd[1]);
        Person person=findPerson(ps);
        if(person==null){
            msg("登録されていません。");
            return;
        }
        msg0("「"+person+"」を削除しますか?");
        String s=input();
        // エラー箇所
        // もし何も押さずにエンターキーでスキップしようとするとエラーが発生する
        // Exception in thread "main" java.lang.StringIndexOutOfBoundsException: String index out of range: 0
	    // at java.base/java.lang.StringLatin1.charAt(StringLatin1.java:48)
	    // at java.base/java.lang.String.charAt(String.java:1512)
	    // at CalendarSystem.deleteSchedule(CalendarSystem.java:215)
	    // at CalendarSystem.exec(CalendarSystem.java:50)
	    // at CalendarSystem.main(CalendarSystem.java:29)
        if(s.charAt(0)=='y'){
            ps_list.remove(person);
        }
    }
}