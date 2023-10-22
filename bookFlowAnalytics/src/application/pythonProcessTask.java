package application;

import java.util.Random;

import org.python.util.PythonInterpreter;

import javafx.concurrent.Task;

public class pythonProcessTask extends Task<Void> {

	@Override
	protected Void call() throws Exception {
		Random random = new Random();
		String code = "";
		try {
			PythonInterpreter python = new PythonInterpreter();
			System.out.println("preparing....");
			for(int i = 0; i < 6; i++)
			{
				int num_code = random.nextInt(10);
				code = code.concat(Integer.toString(num_code));
			}
			System.out.println("this is code we generated" + code);
			String command = "import smtplib\r\n"
					+ "from email.mime.text import MIMEText\r\n"
					+ "from email.mime.multipart import MIMEMultipart\r\n"
					+ "from email.header import Header"
					+ "\r\n"
					
					+ "code = '" + code + "'\r\n"
					+ "msg = MIMEMultipart()\r\n"
					+ "subject = \"Email Verification Code :\"\r\n"
					+ "msg[\"Subject\"] = Header(subject, \"utf-8\")\r\n"
					+ "body = \"Enter this code for signing in : \" + code \r\n"
					+ "msg.attach(MIMEText(body, \"plain\"))\r\n"
					
					+ "sender_email = \"AutoMail.02.11.04@gmail.com\"\r\n"
//					+ "rec_email = \"barimitesh23@gmail.com\"\r\n"
					+ "rec_email = \"varunvisave@gmail.com\"\r\n"
					+ "password = \"tvhdazcwcvzdovcb\"\n"
//					+ "instruction = 'Enter this code login / Sign in : '\r\n"
					+ "message = msg.as_string()\r\n"
					+ "\r\n"
					+ "server = smtplib.SMTP('smtp.gmail.com',587)\r\n"
					+ "server.starttls()\r\n"
					+ "server.login(sender_email,password)\r\n"
					+ "print(\"login succes\")\r\n"
					+ "server.sendmail(sender_email,rec_email,message)\r\n"
					+ "print(\"email has been sent\", rec_email)\r\n"
					+ "server.quit()";
			python.exec(command);
			System.out.println("done...");

			python.close();
		} catch (Exception e) {
			System.out.println("not done we have got an error");
			e.printStackTrace();
		}

		return null;
	}

}
