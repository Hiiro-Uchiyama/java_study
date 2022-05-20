import java.io.*;
import java.util.*;
import java.text.*;

// javac CalendarSystem.java
// java CalendarSystem

public class CalendarSystem{

    public class Schedule {
        public int sn;
        public Date date;
        public String content;

        public Schedule(int s, String dt, String c) throws ParseException{
            sn=s;
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm");
            date=sdf.parse(dt);
            content=c;
        }

        public String toString(){
            return String.format("%04d:%tY/%<tm/%<td %<tH:%<tM %s", sn, date, content);
        }
    }

    private int last_sn;
    private List<Schedule> sc_list;
    private String file_name="schedule.csv";

    public static void main(String[] args){
        (new CalendarSystem()).exec();
    }

    private void exec() {
        initSystem();
        boolean end_flag=false;
        while(!end_flag){
            String[] cmd=inputCommand();
            switch(cmd[0]){
                case "quit": end_flag=true;
                    break;
                case "add": addSchedule(cmd);
                    break;
                case "list": listSchedule(cmd);
                    break;
                case "save": saveSchedules(cmd);
                    break;
                case "load": loadSchedule(cmd);
                    break;
                default:
                    msg("コマンド"+cmd+"がわかりません。");
            }
        }
        quitSystem();
    }

    private void initSystem() {
        msg("*** 予定管理システムSP3へようこそ ***");
        last_sn=0;
        sc_list=new ArrayList<Schedule>();
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

    private String[] inputCommand() {
        msg0("コマンドは？");
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String l=br.readLine();
            return l.split(" ");
        }catch(IOException e){}
        String[] r={ "" };
        return r;
    }

    private void addSchedule(String[] cmd){
        if(cmd.length!=4){
            msg("addコマンドの構文が不正です");
            return;
        }
        try{
            String d=cmd[1]+" "+cmd[2];
            String c=cmd[3];
            sc_list.add(new Schedule(++last_sn,d,c));
        }catch(ParseException e){
            msg("日付が不正です");
        }
    }

    private void listSchedule(String[] cmd){
        for(Schedule s:sc_list){
            msg(" "+s);
        }
    }

    // saveSchedules
    private void saveSchedules(String[] cmd){
        String fn=file_name;
        if(cmd.length==2){
            fn=cmd[1];
        }
        try{
            PrintWriter pw=new PrintWriter(new FileWriter(fn));
            pw.println(sc_list.size());
            for(Schedule s:sc_list){
                String csv=String.format("%d,%tY/%<tm/%<td,%<tH:%<tM,%s", s.sn,s.date,s.content);
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
            sc_list.clear();
            last_sn=0;
            BufferedReader br=new BufferedReader(new FileReader(fn));
            int n=Integer.parseInt(br.readLine());
            for(int i=0;i<n;i++){
                String l=br.readLine();
                String[] tok=l.split(",");
                int sn=Integer.parseInt(tok[0]);
                String d=tok[1]+" "+tok[2];
                String c=tok[3];
                sc_list.add(new Schedule(sn, d, c));
                if(last_sn<sn){
                    last_sn=sn;
                }
            }
            br.close();
        }catch(IOException e){
            msg("読み込み失敗:"+fn);
        }catch(ParseException e){
            msg("CSVファイルが不正です");
        }
    }
}