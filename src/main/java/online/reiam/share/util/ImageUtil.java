package online.reiam.share.util;

import sun.swing.SwingUtilities2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtil {

    public static boolean isImage(InputStream inputStream) {
        try {
            Image image = ImageIO.read(inputStream);
            // 注意：该方法适用的图片格式为 bmp/gif/jpg/png
            return image != null;
        } catch (IOException e) {
            return false;
        }
    }

    public static ByteArrayInputStream createImage(String str) throws IOException {
        Font font = new Font("微软雅黑", Font.BOLD, 50);
        //获取font的样式应用在str上的整个矩形
        AffineTransform affineTransform = AffineTransform.getScaleInstance(1, 1);
        FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, false, false);
        Rectangle2D r = font.getStringBounds(str, fontRenderContext);
        //把单个字符的高度+1保证高度绝对能容纳字符串作为图片的高度
        int height = (int) Math.floor(r.getHeight()) + 1;
        //获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width = (int) Math.round(r.getWidth()) + 1;
        //创建图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(Color.WHITE);
        //先用白色填充整张图片,也就是背景
        g.fillRect(0, 0, width, height);
        //在换成黑色
        g.setColor(Color.black);
        //设置画笔字体
        g.setFont(font);
        //画出字符串
        g.drawString(str, 0, font.getSize());
        g.dispose();
        //返回数据流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (ImageIO.write(bufferedImage, "png", out)) {
            return new ByteArrayInputStream(out.toByteArray());
        }
        return null;
    }

    public static void textToPicture(String text, int fontSize) {
        try {
            Font font = new Font("微软雅黑", Font.BOLD, fontSize);
            Rectangle2D r = font.getStringBounds(text, new FontRenderContext(AffineTransform.getScaleInstance(1, 1), false, false));
            //获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
            int width = (int) Math.round(r.getWidth()) + 1;
            //把单个字符的高度+1保证高度绝对能容纳字符串作为图片的高度
            int height = (int) Math.floor(r.getHeight()) + 1;
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D) bi.getGraphics();
            g2.setBackground(null);
            g2.clearRect(0, 0, width, height);
            g2.setFont(font);
            g2.setPaint(Color.BLACK);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            printString(g2, text, (width - (text.length() * fontSize)) / 2, (height - fontSize) / 2 + 40, fontSize);
            g2.dispose();
            String filepath = "D://" + "test" + ".png";
            File file = new File(filepath);
            ImageIO.write(bi, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printString(Graphics2D g2d, String str, int x, int y, int fontSize) {
        FontMetrics metrics = SwingUtilities2.getFontMetrics(null, g2d.getFont());
        for (char ca : str.toCharArray()) {
            int px = metrics.stringWidth("" + ca);
            g2d.drawString("" + ca, x + (fontSize - px) / 2, y);
            x += fontSize;
        }
    }

    public static void main(String[] args) {
        textToPicture("中文生成图片", 50);
    }

}
