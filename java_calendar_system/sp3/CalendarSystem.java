import java.io.*;
import java.util.*;
import java.text.*;

public class CalendarSystem{

    public class Schedule {
        private int sn;
        private Date date;
        private String content;

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
}