import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {

    public void work() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8880));
            SocketChannel socketChannel = serverSocketChannel.accept();
            final ByteBuffer byteBuffer = ByteBuffer.allocate(2 << 10);
            while (socketChannel.isConnected()) {
                int bytesCount = socketChannel.read(byteBuffer);
                if (bytesCount == -1) break;
                final String msg = new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                byteBuffer.clear();
                socketChannel.write(ByteBuffer.wrap(msg.replaceAll(" ", "").getBytes(StandardCharsets.UTF_8)));
            }
            socketChannel.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}