// 토큰을 저장할 변수
let token = "";
let clientdata;

window.sendMessage = function () {
    let messageInput = document.getElementById("messageInput");
    let message = messageInput.value;
    if (message === "" || !clientdata.connected) {
        return;
    }

    clientdata.send("/app/chat/" + 6, {}, JSON.stringify({
        roomId: 12,
        content: message,
        userId: 45,
    }));

    messageInput.value = "";
};

function login() {
    fetch("http://localhost:9090/api/user/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            email: "thanos@gmail.com",
            password: "1234",
        }),
    })
        .then((response) => {
            // 'Authorization' 헤더에서 토큰을 읽어옴
            let authHeader = response.headers.get('Authorization');
            if (authHeader) {
                token = authHeader.replace('Bearer ', '');  // 'Bearer ' 제거
                console.log('토큰 : ' + token);  // 토큰 출력
            } else {
                console.error('응답에서 Authorization 헤더를 찾을 수 없습니다.');
            }

            // WebSocket 연결
            connectWebSocket();
        });
}

const connectWebSocket = () => {
    // 토큰이 제대로 설정되지 않았다면 연결 X
    if (!token) {
        console.error('토큰이 설정되지 않았습니다. WebSocket에 연결할 수 없습니다.');
        return;
    }
    // SockJS를 사용하여 WebSocket 연결 생성
    let socket = new SockJS(`http://localhost:9090/ws?token=${token}`);
    // Stomp 클라이언트 생성
    clientdata = Stomp.over(socket);

    clientdata.connect({}, function(frame) {
        // 연결 성공
        console.log('연결됨 : ' + frame);
    }, function(error) {
        // 연결 실패
        console.log('에러 : ' + error);
    });
};

login(); // 로그인 함수 호출