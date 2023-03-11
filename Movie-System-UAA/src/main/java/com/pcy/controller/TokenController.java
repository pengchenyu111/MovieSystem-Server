//package com.pcy.controller;
//
//import cn.wolfcode.common.domain.UserInfo;
//import cn.wolfcode.common.web.Result;
//import cn.wolfcode.domain.UserLogin;
//import cn.wolfcode.domain.UserResponse;
//import cn.wolfcode.service.IUserService;
//import cn.wolfcode.util.MD5Util;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.RandomAccessFile;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by wolfcode-lanxw
// */
//@RestController
//@RequestMapping("/token")
//public class TokenController {
//    @Autowired
//    private IUserService userService;
//    @RequestMapping("/initData")
//    @Transactional
//    public Result<String> initData() throws Exception {
//        List<UserLogin> userLoginList = initUserLogin(500);//在内存中创建500个User对象
//        createToken(userLoginList);
//        return Result.success("");
//    }
//    private List<UserLogin> initUserLogin(int count) throws Exception {
//        List<UserLogin> userLoginList = new ArrayList<>();
//        List<UserInfo> userInfoList = new ArrayList<>();
//        for(int i=0;i<count;i++){
//            UserLogin userLogin = new UserLogin();
//            userLogin.setPhone(13000000000L+i);
//            userLogin.setPassword("123456");
//            userLogin.setSalt("1a2b3c4d5e");
//            userLoginList.add(userLogin);
//
//            UserInfo userInfo = new UserInfo();
//            userInfo.setNickName("测试账号"+i);
//            userInfo.setPhone(userLogin.getPhone());
//            userInfo.setBirthDay("1990-01-01");
//            userInfo.setHead("1.jpg");
//            userInfo.setInfo("");
//            userInfoList.add(userInfo);
//
//        }
//        //insertUserLoginToDb(userLoginList);
//        //insertUserInfoToDb(userInfoList);
//        return userLoginList;
//    }
//    private void insertUserLoginToDb(List<UserLogin> userLoginList) throws Exception {
//        Connection conn = getConn();
//        String sql = "insert into t_user_login(phone, password,salt)values(?,?,?)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        for(int i=0;i<userLoginList.size();i++) {
//            UserLogin userLogin = userLoginList.get(i);
//            pstmt.setLong(1, userLogin.getPhone());
//            pstmt.setString(2, MD5Util.encode(userLogin.getPassword(),userLogin.getSalt()));
//            pstmt.setString(3, userLogin.getSalt());
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.close();
//        conn.close();
//    }
//    private void insertUserInfoToDb(List<UserInfo> userInfoList) throws Exception {
//        Connection conn = getConn();
//        String sql = "insert into t_user_info(phone,nickname,head,birthDay,info)values(?,?,?,?,?)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        for(int i=0;i<userInfoList.size();i++) {
//            UserInfo userInfo = userInfoList.get(i);
//            pstmt.setLong(1, userInfo.getPhone());
//            pstmt.setString(2, userInfo.getNickName());
//            pstmt.setString(3, userInfo.getHead());
//            pstmt.setString(4, userInfo.getBirthDay());
//            pstmt.setString(5, userInfo.getInfo());
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.close();
//        conn.close();
//    }
//
//    private void createToken(List<UserLogin> userLoginList) throws Exception {
//        File file = new File("D:/tokens.txt");
//        if(file.exists()) {
//            file.delete();
//        }
//        RandomAccessFile raf = new RandomAccessFile(file, "rw");
//        file.createNewFile();
//        raf.seek(0);
//        for(int i=0;i<userLoginList.size();i++) {
//            UserLogin userLogin = userLoginList.get(i);
//            UserResponse login = userService.login(userLogin.getPhone(), userLogin.getPassword(), "0:0:0:0:0:0:0:1");
//            String row = login.getToken();
//            raf.seek(raf.length());
//            raf.write(row.getBytes());
//            raf.write("\r\n".getBytes());
//        }
//        raf.close();
//    }
//
//    public static Connection getConn() throws Exception{
//        String url = "jdbc:mysql://192.168.113.204:3306/shop-uaa?serverTimezone=GMT%2B8";
//        String username = "root";
//        String password = "WolfCode_2017";
//        String driver = "com.mysql.jdbc.Driver";
//        Class.forName(driver);
//        return DriverManager.getConnection(url,username, password);
//    }
//
//}
