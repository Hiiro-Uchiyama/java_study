import java.io.*;

public class CalendarSystem{
    public static void main(String[] args){
        (new CalendarSystem()).exec();
    }

    private void exec() {
        initSystem();
        boolean end_flag=false;
        while(!end_flag){
            String cmd=inputCommand();
            switch(cmd){
                case "quit": end_flag=true;
                break;
                default:
                    msg("コマンド"+cmd+"がわかりません。");
            }
        }
        quitSystem();
    }

    private void initSystem() {
        msg("*** 予定管理システムSP1へようこそ ***");
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

    private String inputCommand() {
        msg0("コマンドは？");
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        }catch(IOException e){}
        return "";
    }


}