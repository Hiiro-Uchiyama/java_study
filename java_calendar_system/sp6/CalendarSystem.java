import java.io.*;
import java.util.*;
import java.text.*;

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
                case "list": listSchedule(cmd);
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
}