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
                case "help": showHelp();
                break;
                default:
                    msg("コマンド"+cmd+"がわかりません。");
            }
        }
        quitSystem();
    }

    private void initSystem() {
        msg("*** 予定管理システムSP2へようこそ ***");
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