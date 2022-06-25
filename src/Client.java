import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public void work() {
        try (Scanner scanner = new Scanner(System.in);
             SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress("localhost", 8880));
            final ByteBuffer byteBuffer = ByteBuffer.allocate(2 << 10);
            StringBuilder sb = new StringBuilder();
            while (true) {
                System.out.println("Введите строку для удаления пробелов или end для получения результата");
                String msg = scanner.nextLine();
                if (msg.equals("end")) break;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(byteBuffer);
                sb.append(new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8));
                byteBuffer.clear();
            }
            System.out.println(sb);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}