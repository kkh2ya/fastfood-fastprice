package bread.main;

import bread.chatserver.ChatSocketManager;

/**
 * 채팅 메인.
 * 채팅 연결 소캣 호출.
 * @author 정동영
 * */
public class ChatMain {

	public static void main(String[] args) {
		new ChatSocketManager();
	}

}
