let stompClient;

function connect(IuserId) {

  let userId=IuserId;

  if(userId){
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
  }

}

function sendMessage(Imessage,ItargetUserId) {
  let messageContent = Imessage;

  if(messageContent && stompClient) {
    console.log("send Message");
    let alarm = {
      type: 'COMMENT',
      content: messageContent,
      targetUserId: ItargetUserId,
    };
    stompClient.send("/app/board/"+ItargetUserId, {}, JSON.stringify(alarm));
  }

}

function onMessageReceived(payload) {

  let messageArea = document.querySelector('.messageArea');
  console.log("receive message");
  let message = JSON.parse(payload.body);

  let messageElement = document.createElement('span');

  messageElement.classList.add('alarm-message');

  let usernameElement = document.createElement('p');
  let contentText = document.createTextNode(message.content);
  usernameElement.appendChild(contentText);
  messageElement.appendChild(usernameElement);

  messageArea.appendChild(messageElement);
}



function onConnected() {
  stompClient.subscribe('/user/'+userId, onMessageReceived);
  console.log("on connected");
}


function onError(error) {
  console.log("error");
}




