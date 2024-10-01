import { WebSocket, WebSocketServer } from "ws";

// Store clients in a map with user IDs
const clients = new Map();

const wss = new WebSocketServer({ port: 3002 });

wss.on("connection", (ws) => {
  console.log("Client connected");

  ws.on("message", (message) => {
    try {
      const data = JSON.parse(message);

      if (data.type === "register") {
        // Register user ID with WebSocket
        clients.set(data.userId, ws);
        console.log(`User ${data.userId} registered`);
      }

      if (data.type === "private_message") {
        // Send a private message
        const recipientSocket = clients.get(data.receiverId);
        if (recipientSocket && recipientSocket.readyState === WebSocket.OPEN) {
          recipientSocket.send(
            JSON.stringify({
              type: data.type,
              chat: data.chat,
              senderId: data.senderId,
              sender: data.sender,
              receiverId: data.receiverId,
              senderAvatar: data.senderAvatar,
              createdAt: data.createdAt,
            })
          );
          console.log("Message sent to:", data.receiverId);
        } else {
          console.log(
            `User ${data.receiverId} not connected or WebSocket closed.`
          );
        }
      }

      if (data.type === "recall") {
        const recipientSocket = clients.get(data.receiverId);
        if (recipientSocket && recipientSocket.readyState === WebSocket.OPEN) {
          recipientSocket.send(
            JSON.stringify({
              type: data.type,
              id: data.id,
              receiverId: data.receiverId,
            })
          );
          console.log("Recall sent to:", data.receiverId);
        } else {
          console.log(
            `User ${data.receiverId} not connected or WebSocket closed.`
          );
        }
      }
    } catch (error) {
      console.error("Error processing message:", error);
    }
  });

  ws.on("close", () => {
    // Remove the disconnected client from the clients map
    for (const [userId, socket] of clients.entries()) {
      if (socket === ws) {
        clients.delete(userId);
        console.log(`User ${userId} disconnected`);
        break;
      }
    }
  });
});

console.log("WebSocket server running on ws://localhost:3002");
