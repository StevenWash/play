/*
Navicat MySQL Data Transfer
 
Source Server         : mysqllocal
Source Server Version : 50152
Source Host           : localhost:3306
Source Database       : pss
 
Target Server Type    : MYSQL
Target Server Version : 50152
File Encoding         : 65001
 
Date: 2014-03-07 10:43:09
*/
 
SET FOREIGN_KEY_CHECKS=0;
 
-- ----------------------------
-- Table structure for sl_company
-- ----------------------------
DROP TABLE IF EXISTS `sl_company`;
CREATE TABLE `sl_company` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(50) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `web` varchar(45) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `postcode` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of sl_company
-- ----------------------------
INSERT INTO `sl_company` VALUES ('1', '锐客科技', '上海锐客科技股份有限公司', 'www.codeman.cn', '上海市浦东新区峨山路91弄97号陆家嘴软件园5号楼3层', '200127', '4008202993', 'not-contact-me@codeman.cn');
 
-- ----------------------------
-- Table structure for sl_dept
-- ----------------------------
DROP TABLE IF EXISTS `sl_dept`;
CREATE TABLE `sl_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `dept_name` varchar(50) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`dept_id`),
  KEY `fk_parent_id` (`parent_id`),
  KEY `fk_company_id` (`company_id`),
  CONSTRAINT `fk_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `sl_dept` (`dept_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_company_id` FOREIGN KEY (`company_id`) REFERENCES `sl_company` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of sl_dept
-- ----------------------------
INSERT INTO `sl_dept` VALUES ('1', null, '北京分公司', null);
INSERT INTO `sl_dept` VALUES ('2', null, '上海分公司', null);
INSERT INTO `sl_dept` VALUES ('3', null, '深圳分公司', null);
INSERT INTO `sl_dept` VALUES ('4', '1', '北京-软件研发部', null);
INSERT INTO `sl_dept` VALUES ('5', '1', '北京-软件产品支持部', null);
INSERT INTO `sl_dept` VALUES ('6', '1', '北京-软件销售部', null);
INSERT INTO `sl_dept` VALUES ('7', '1', '北京-软件技术支持部', null);
INSERT INTO `sl_dept` VALUES ('8', '2', '上海-软件研发部', null);
INSERT INTO `sl_dept` VALUES ('9', '2', '上海-软件产品支持部', null);
INSERT INTO `sl_dept` VALUES ('10', '2', '上海-软件销售部', null);
INSERT INTO `sl_dept` VALUES ('11', '2', '上海-软件技术支持部', null);
INSERT INTO `sl_dept` VALUES ('12', '3', '深圳-软件研发部', null);
INSERT INTO `sl_dept` VALUES ('13', '3', '深圳-软件产品支持部', null);
INSERT INTO `sl_dept` VALUES ('14', '3', '深圳-软件销售部', null);
INSERT INTO `sl_dept` VALUES ('15', '3', '深圳-软件技术支持部', null);
 
-- ----------------------------
-- Table structure for sl_employee
-- ----------------------------
DROP TABLE IF EXISTS `sl_employee`;
CREATE TABLE `sl_employee` (
  `EMPLOYEE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMPLOYEE_CODE` varchar(20) DEFAULT NULL,
  `USER_NAME` varchar(20) DEFAULT NULL,
  `DEPT_ID` int(11) DEFAULT NULL,
  `EMPLOYEE_NAME` varchar(16) DEFAULT NULL,
  `SEX` bit(1) NOT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `MARRIED` bit(1) NOT NULL,
  `SALARY` decimal(10,2) DEFAULT NULL,
  `DEGREE` varchar(30) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `WEB` varchar(50) DEFAULT NULL,
  `CMNT` varchar(255) DEFAULT NULL,
  `IMAGE` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `ANIMAL` varchar(45) DEFAULT NULL,
  `PHONE` varchar(45) DEFAULT NULL,
  `MOBILE` varchar(45) DEFAULT NULL,
  `POSITION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`EMPLOYEE_ID`),
  KEY `fk_dept_id` (`DEPT_ID`),
  CONSTRAINT `fk_dept_id` FOREIGN KEY (`DEPT_ID`) REFERENCES `sl_dept` (`dept_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of sl_employee
-- ----------------------------
INSERT INTO `sl_employee` VALUES ('1', '000001', 'ANLIN', '4', '安林', '\0', '1980-05-17 00:00:00', '', '4235.00', '硕士', 'changlin@mailServer.com', 'www.changlin.com', null, '1119466836427.gif', '123456', '猴', '42785631', '13103076337', '软件工程师');
INSERT INTO `sl_employee` VALUES ('2', '000002', 'BAIXIAOBO', '15', '白小波', '', '1979-05-10 00:00:00', '', '2034.00', '硕士', 'chenbo@mailServer.com', 'www.chenbo.com', '爱好足球、游泳    ;\n信奉基督教', '1119466841294.gif', '123456', '羊', '56257106', '13244539664', '软件工程师');
INSERT INTO `sl_employee` VALUES ('3', '000003', 'CHENGYU', '15', '程玉', '\0', '1973-02-18 00:00:00', '\0', '6020.00', '大专', 'chengyu@mailServer.com', 'www.chengyu.com', null, '1119466845971.gif', '123456', '牛', '80314824', '13045063418', '软件工程师');
INSERT INTO `sl_employee` VALUES ('4', '000004', 'CHENHAO', '5', '陈昊', '', '1979-10-07 00:00:00', '\0', '3015.00', '博士', 'chenhao@mailServer.com', 'www.chenhao.com', '', '1119466851909.gif', '123456', null, '16769292', '13096908093', '软件工程师');
INSERT INTO `sl_employee` VALUES ('5', '000005', 'DENGIUXIAN', '11', '邓秀贤', '\0', '1972-04-11 00:00:00', '', '3384.00', '本科', 'chenxiuxian@mailServer.com', 'www.chenxiuxian.com', 'DSR项目负责人    ;\nJet2002作者', '1119466859200.gif', '123456', null, '78045874', '13143058562', '软件工程师');
INSERT INTO `sl_employee` VALUES ('6', '000006', 'FANGSHIZE', '10', '方世泽', '', '1973-03-29 00:00:00', '\0', '5878.00', '高中', 'fangshize@mailServer.com', 'www.fangshize.com', null, '1119466863436.gif', '123456', null, '57435342', '13050865210', '软件工程师');
INSERT INTO `sl_employee` VALUES ('7', '000007', 'FENGJIE', '8', '冯婕', '\0', '1975-07-05 00:00:00', '', '3613.00', '博士', 'fengjie@mailServer.com', 'www.fengjie.com', null, null, '123456', null, '43794710', '13160420139', '软件工程师');
INSERT INTO `sl_employee` VALUES ('8', '000008', 'GUAILING', '13', '古爱玲', '\0', '1976-08-06 00:00:00', '\0', '2828.00', '中专', 'guailing@mailServer.com', 'www.guailing.com', null, null, '123456', null, '23319582', '13081612154', '软件工程师');
INSERT INTO `sl_employee` VALUES ('9', '000009', 'GUOJINGLONG', '7', '郭景龙', '', '1979-09-11 00:00:00', '', '3877.00', '中专', 'guojinglong@mailServer.com', 'www.guojinglong.com', null, '', '123456', null, '34567605', '13056642211', '软件工程师');
INSERT INTO `sl_employee` VALUES ('10', '000010', 'GUOLIWEI', '5', '国莅伟', '\0', '1978-11-15 00:00:00', '\0', '4900.00', '本科', 'guoliwei@mailServer.com', 'www.guoliwei.com', null, '', '123456', null, '79597952', '13230581175', '软件工程师');
INSERT INTO `sl_employee` VALUES ('11', '000011', 'HETAO', '5', '何涛', '', '1978-10-14 00:00:00', '\0', '2410.00', '博士后', 'hetao@mailServer.com', 'www.hetao.com', null, null, '123456', null, '11891437', '13292552627', '软件工程师');
INSERT INTO `sl_employee` VALUES ('12', '000012', 'HEZONGSI', '13', '何宗思', '', '1976-03-15 00:00:00', '', '4373.00', '大专', 'hezongsi@mailServer.com', 'www.hezongsi.com', '书法特长', null, '123456', null, '55213450', '13015327698', '软件工程师');
INSERT INTO `sl_employee` VALUES ('13', '000013', 'HONGJINGZHU', '12', '红景柱', '', '1979-03-09 00:00:00', '\0', '2352.00', '大专', 'hongjingzhu@mailServer.com', 'www.hongjingzhu.com', null, null, '123456', null, '93059244', '13007852999', '软件工程师');
INSERT INTO `sl_employee` VALUES ('14', '000014', 'HUAYUYAN', '7', '华玉岩', '\0', '1978-11-03 00:00:00', '\0', '6204.00', '大专', 'huayuyan@mailServer.com', 'www.huayuyan.com', null, null, '123456', null, '48902845', '13222306860', '软件工程师');
INSERT INTO `sl_employee` VALUES ('15', '000015', 'HUWENPENG', '11', '胡文鹏', '', '1979-12-22 00:00:00', '', '2298.00', '高中', 'huwenpeng@mailServer.com', 'www.huwenpeng.com', null, null, '123456', null, '60444248', '13296547666', '软件工程师');
INSERT INTO `sl_employee` VALUES ('16', '000016', 'JIANGLEI', '5', '姜磊', '', '1974-08-29 00:00:00', '\0', '3466.00', '硕士', 'jianglei@mailServer.com', 'www.jianglei.com', null, null, '123456', null, '24313166', '13234123462', '软件工程师');
INSERT INTO `sl_employee` VALUES ('17', '000017', 'JINYI', '13', '金一', '\0', '1972-10-13 00:00:00', '\0', '6586.00', '大专', 'jinyi@mailServer.com', 'www.jinyi.com', null, null, '123456', null, '99672930', '13104653040', '软件工程师');
INSERT INTO `sl_employee` VALUES ('18', '000018', 'LIFUYING', '7', '李福迎', '', '1978-07-22 00:00:00', '\0', '3839.00', '高中', 'lifuying@mailServer.com', 'www.lifuying.com', null, null, '123456', null, '53243971', '13295580868', '软件工程师');
INSERT INTO `sl_employee` VALUES ('19', '000019', 'LINJUN', '6', '林俊', '', '1977-11-06 00:00:00', '', '5873.00', '本科', 'linjun@mailServer.com', 'www.linjun.com', null, null, '123456', null, '97048204', '13249448675', '软件工程师');
INSERT INTO `sl_employee` VALUES ('20', '000020', 'LIUHUAI', '11', '刘怀', '', '1980-01-19 00:00:00', '\0', '3639.00', '博士后', 'liuhuai@mailServer.com', 'www.liuhuai.com', null, null, '123456', null, '76051523', '13146536367', '软件工程师');
INSERT INTO `sl_employee` VALUES ('21', '000021', 'LIUJIANGUO', '5', '刘建国', '', '1973-07-31 00:00:00', '\0', '5488.00', '高中', 'liujianguo@mailServer.com', 'www.liujianguo.com', null, null, '123456', null, '63550858', '13259163063', '软件工程师');
INSERT INTO `sl_employee` VALUES ('22', '000022', 'LIUSHAONAN', '15', '刘少南', '', '1977-11-28 00:00:00', '', '6001.00', '本科', 'liushaonan@mailServer.com', 'www.liushaonan.com', null, null, '123456', null, '46453032', '13069218260', '软件工程师');
INSERT INTO `sl_employee` VALUES ('23', '000023', 'LIUYUNBO', '8', '刘云波', '', '1977-11-26 00:00:00', '\0', '5589.00', '大专', 'liuyunbo@mailServer.com', 'www.liuyunbo.com', null, null, '123456', null, '10820340', '13073762736', '软件工程师');
INSERT INTO `sl_employee` VALUES ('24', '000024', 'LIUZHANWEI', '14', '刘占卫', '', '1977-01-21 00:00:00', '\0', '3533.00', '本科', 'liuzhanwei@mailServer.com', 'www.liuzhanwei.com', null, null, '123456', null, '29677567', '13274518494', '测试工程师');
INSERT INTO `sl_employee` VALUES ('25', '000025', 'LIXINMIN', '13', '李新民', '', '1973-03-29 00:00:00', '', '2813.00', '博士', 'lixinmin@mailServer.com', 'www.lixinmin.com', null, null, '123456', null, '67124430', '13210561735', '测试工程师');
INSERT INTO `sl_employee` VALUES ('26', '000026', 'LIZHENGXIN', '8', '李正欣', '\0', '1979-07-26 00:00:00', '\0', '3603.00', '中专', 'lizhengxin@mailServer.com', 'www.lizhengxin.com', null, null, '123456', null, '98776780', '13149427940', '测试工程师');
INSERT INTO `sl_employee` VALUES ('27', '000027', 'MASHUQI', '5', '马树奇', '', '1977-09-17 00:00:00', '\0', '4330.00', '硕士', 'mashuqi@mailServer.com', 'www.mashuqi.com', null, null, '123456', null, '48217361', '13289248448', '测试工程师');
INSERT INTO `sl_employee` VALUES ('28', '000028', 'MAWENLIANG', '5', '马文良', '', '1975-06-28 00:00:00', '', '3233.00', '大专', 'mawenliang@mailServer.com', 'www.mawenliang.com', null, null, '123456', null, '93843142', '13069294076', '测试工程师');
INSERT INTO `sl_employee` VALUES ('29', '000029', 'NAXIN', '9', '那鑫', '\0', '1976-01-01 00:00:00', '\0', '6128.00', '高中', 'naxin@mailServer.com', 'www.naxin.com', null, null, '123456', null, '44660979', '13100650096', '测试工程师');
INSERT INTO `sl_employee` VALUES ('30', '000030', 'PANLONG', '13', '盘龙', '', '1976-04-03 00:00:00', '\0', '3395.00', '硕士', 'panlong@mailServer.com', 'www.panlong.com', '', '', '123456', null, '29988843', '13093415134', '测试工程师');
INSERT INTO `sl_employee` VALUES ('31', '000031', 'QIUZHONGPAN', '6', '邱仲潘', '', '1971-02-14 00:00:00', '\0', '4408.00', '小学', 'qiuzhongpan@mailServer.com', 'www.qiuzhongpan.com', null, null, '123456', null, '47194235', '13286424498', '测试工程师');
INSERT INTO `sl_employee` VALUES ('32', '000032', 'SHENGCHANLI', '4', '盛传丽', '\0', '1981-12-10 00:00:00', '', '6371.00', '大专', 'shengchanli@mailServer.com', 'www.shengchanli.com', null, null, '123456', null, '18023624', '13004741092', '测试工程师');
INSERT INTO `sl_employee` VALUES ('33', '000033', 'TONGDAWEI', '9', '佟大为', '', '1975-02-15 00:00:00', '\0', '3436.00', '高中', 'tongdawei@mailServer.com', 'www.tongdawei.com', null, null, '123456', null, '56606891', '13151440003', '测试工程师');
INSERT INTO `sl_employee` VALUES ('34', '000034', 'WANGDONG', '14', '王栋', '', '1979-02-13 00:00:00', '\0', '5863.00', '本科', 'wangdong@mailServer.com', 'www.wangdong.com', null, null, '123456', null, '73804190', '13165613051', '测试工程师');
INSERT INTO `sl_employee` VALUES ('35', '000035', 'WANGWEIBIN', '7', '王维斌', '', '1979-10-16 00:00:00', '', '6882.00', '本科', 'wangweibin@mailServer.com', 'www.wangweibin.com', null, null, '123456', null, '73039503', '13273778303', '测试工程师');
INSERT INTO `sl_employee` VALUES ('36', '000036', 'WANGWENQING', '10', '王文清', '\0', '1972-01-21 00:00:00', '\0', '4462.00', '博士', 'wangwenqing@mailServer.com', 'www.wangwenqing.com', null, null, '123456', null, '41994347', '13200660999', '测试工程师');
INSERT INTO `sl_employee` VALUES ('37', '000037', 'WANGXIAODONG', '10', '王小栋', '', '1980-03-08 00:00:00', '\0', '6439.00', '中专', 'wangxiaodong@mailServer.com', 'www.wangxiaodong.com', null, null, '123456', null, '27850967', '13297471604', '测试工程师');
INSERT INTO `sl_employee` VALUES ('38', '000038', 'WANGYONGFU', '9', '王永富', '', '1976-06-18 00:00:00', '', '6136.00', '本科', 'wangyongfu@mailServer.com', 'www.wangyongfu.com', null, null, '123456', null, '96301813', '13103856707', '测试工程师');
INSERT INTO `sl_employee` VALUES ('39', '000039', 'WANGYOUMING', '8', '王友明', '', '1975-02-13 00:00:00', '', '2101.00', '博士后', 'wangyouming@mailServer.com', 'www.wangyouming.com', null, null, '123456', null, '61137461', '13240762192', '测试工程师');
INSERT INTO `sl_employee` VALUES ('40', '000040', 'WANGZAIYI', '4', '王在义', '', '1977-04-14 00:00:00', '', '2705.00', '本科', 'wangzaiyi@mailServer.com', 'www.wangzaiyi.com', null, null, '123456', null, '84628277', '13206515245', '测试工程师');
INSERT INTO `sl_employee` VALUES ('41', '000041', 'WANGZHOULIANG', '12', '王忠亮', '', '1971-08-30 00:00:00', '\0', '2717.00', '中专', 'wangzhouliang@mailServer.com', 'www.wangzhouliang.com', null, null, '123456', null, '37014215', '13159790338', '测试工程师');
INSERT INTO `sl_employee` VALUES ('42', '000042', 'WUZHNGMIN', '14', '吴忠民', '', '1973-08-27 00:00:00', '', '4504.00', '中专', 'wuzhngmin@mailServer.com', 'www.wuzhngmin.com', null, null, '123456', null, '52945280', '13151324836', '测试工程师');
INSERT INTO `sl_employee` VALUES ('43', '000043', 'XIAOGUIFENG', '8', '肖桂峰', '', '1979-05-24 00:00:00', '', '2108.00', '博士', 'xiaoguifeng@mailServer.com', 'www.xiaoguifeng.com', null, null, '123456', null, '10075234', '13058088650', '测试工程师');
INSERT INTO `sl_employee` VALUES ('44', '000044', 'XINLIANBO', '6', '辛连波', '', '1976-09-06 00:00:00', '', '4964.00', '本科', 'xinlianbo@mailServer.com', 'www.xinlianbo.com', null, '', '123456', null, '51688556', '13247707463', '测试工程师');
INSERT INTO `sl_employee` VALUES ('45', '000045', 'XUEYI', '12', '薛毅', '', '1978-02-07 00:00:00', '', '2048.00', '大专', 'xueyi@mailServer.com', 'www.xueyi.com', null, null, '123456', null, '19175830', '13000600708', '测试工程师');
INSERT INTO `sl_employee` VALUES ('46', '000046', 'XULIJUN', '7', '许立军', '', '1978-12-19 00:00:00', '\0', '5872.00', '本科', 'xulijun@mailServer.com', 'www.xulijun.com', null, null, '123456', null, '64672998', '13040396064', '测试工程师');
INSERT INTO `sl_employee` VALUES ('47', '000047', 'XUXINHUA', '14', '徐新华', '', '1970-01-21 00:00:00', '', '5253.00', '硕士', 'xuxinhua@mailServer.com', 'www.xuxinhua.com', null, null, '123456', null, '42284636', '13250652814', '测试工程师');
INSERT INTO `sl_employee` VALUES ('48', '000048', 'YANGJIANLI', '4', '杨建利', '', '1974-08-30 00:00:00', '\0', '5852.00', '本科', 'yangjianli@mailServer.com', 'www.yangjianli.com', null, null, '123456', null, '31504987', '13162871743', '配置管理员');
INSERT INTO `sl_employee` VALUES ('49', '000049', 'YANGXIAOBIN', '10', '杨晓斌', '\0', '1972-03-09 00:00:00', '\0', '5545.00', '硕士', 'yangxiaobin@mailServer.com', 'www.yangxiaobin.com', null, '', '123456', null, '14639529', '13080672737', '配置管理员');
INSERT INTO `sl_employee` VALUES ('50', '000050', 'YIXIN', '10', '依心', '\0', '1976-09-01 00:00:00', '\0', '2745.00', '大专', 'yixin@mailServer.com', 'www.renzaixin.com', null, '', '123456', null, '97732796', '13161563214', '配置管理员');
INSERT INTO `sl_employee` VALUES ('51', '000051', 'YUJUN', '9', '于俊', '', '1979-10-04 00:00:00', '', '4787.00', '中专', 'yujun@mailServer.com', 'www.yujun.com', null, '', '123456', null, '76697821', '13279831886', '配置管理员');
INSERT INTO `sl_employee` VALUES ('52', '000052', 'ZHANGDEBIN', '9', '张德斌', '', '1971-04-28 00:00:00', '\0', '3030.00', '博士', 'zhangdebin@mailServer.com', 'www.zhangdebin.com', null, null, '123456', null, '81325110', '13094232321', '配置管理员');
INSERT INTO `sl_employee` VALUES ('53', '000053', 'ZHANGLIANG', '10', '张量', '', '1977-02-08 00:00:00', '', '5405.00', '本科', 'zhangliang@mailServer.com', 'www.zhangliang.com', null, null, '123456', null, '22631666', '13290818207', '配置管理员');
INSERT INTO `sl_employee` VALUES ('54', '000054', 'ZHANGSHIBIN', '9', '张世斌', '', '1972-08-13 00:00:00', '', '4964.00', '本科', 'zhangshibin@mailServer.com', 'www.zhangshibin.com', null, null, '123456', null, '57732325', '13116420177', '配置管理员');
INSERT INTO `sl_employee` VALUES ('55', '000055', 'ZHANGXINEN', '15', '张秀恩', '\0', '1979-07-30 00:00:00', '', '6777.00', '硕士', 'zhangxinen@mailServer.com', 'www.zhangxinen.com', null, null, '123456', null, '37534483', '13225330019', '项目经理');
INSERT INTO `sl_employee` VALUES ('56', '000056', 'ZHANGYUEYOU', '13', '张悦友', '', '1978-05-22 00:00:00', '', '5220.00', '中专', 'zhangyueyou@mailServer.com', 'www.zhangyueyou.com', null, null, '123456', null, '32682801', '13130183409', '项目经理');
INSERT INTO `sl_employee` VALUES ('57', '000057', 'ZHENGQI', '4', '郑琪', '\0', '1970-02-14 00:00:00', '\0', '6991.00', '本科', 'zhengqi@mailServer.com', 'www.zhengqi.com', null, null, '123456', null, '86813757', '13299492093', '项目经理');
INSERT INTO `sl_employee` VALUES ('58', '000058', 'ZHOUSHUQING', '6', '周淑清', '\0', '1976-11-16 00:00:00', '\0', '3218.00', '博士', 'zhoushuqing@mailServer.com', 'www.zhoushuqing.com', null, null, '123456', null, '19815873', '13041188316', '项目经理');
INSERT INTO `sl_employee` VALUES ('59', '000059', 'ZHOUZHENYA', '13', '周振亚', '', '1977-10-23 00:00:00', '\0', '5380.00', '本科', 'zhouzhenya@mailServer.com', 'www.zhouzhenya.com', null, null, '123456', null, '84497522', '13187803586', '项目经理');
INSERT INTO `sl_employee` VALUES ('60', '000060', 'ZHUJUN', '8', '朱军', '', '1973-07-15 00:00:00', '', '3478.00', '本科', 'zhujun@mailServer.com', 'www.zhujun.com', null, null, '123456', null, '11885177', '13081140201', '项目经理');
 
-- ----------------------------
-- Table structure for sl_menu
-- ----------------------------
DROP TABLE IF EXISTS `sl_menu`;
CREATE TABLE `sl_menu` (
  `MENU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_NAME` varchar(60) NOT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `REMARKS` varchar(120) DEFAULT NULL,
  `ICON` varchar(120) DEFAULT NULL,
  `SHOW_ORDER` varchar(45) DEFAULT NULL,
  `URL` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`),
  KEY `fk_qs_menu_parent` (`PARENT_ID`),
  KEY `fk_menu_id` (`PARENT_ID`),
  CONSTRAINT `fk_menu_id` FOREIGN KEY (`PARENT_ID`) REFERENCES `sl_menu` (`MENU_ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of sl_menu
-- ----------------------------
INSERT INTO `sl_menu` VALUES ('1', '系统', null, null, null, '1', null);
INSERT INTO `sl_menu` VALUES ('2', '通讯录', null, null, null, '2', null);
INSERT INTO `sl_menu` VALUES ('3', '基础数据维护', null, null, null, '3', null);
INSERT INTO `sl_menu` VALUES ('4', '个人信息', null, null, null, '4', null);
INSERT INTO `sl_menu` VALUES ('5', '消息', null, null, null, '5', null);
INSERT INTO `sl_menu` VALUES ('6', '欢迎', '1', null, ' url(>skin>common/icons.gif) -0px -20px', '1', 'com.bstek.dorado.sample.standardlesson.junior.system.Welcome.d');
INSERT INTO `sl_menu` VALUES ('7', '系统信息', '1', null, null, '2', 'com.bstek.dorado.sample.standardlesson.junior.system.SystemInfo.d');
INSERT INTO `sl_menu` VALUES ('8', '帮助', '1', null, null, '3', 'http://wiki.bsdn.org');
INSERT INTO `sl_menu` VALUES ('9', '登出', '1', null, null, '4', null);
INSERT INTO `sl_menu` VALUES ('10', '企业联系方式', '2', null, null, '1', 'com.bstek.dorado.sample.standardlesson.junior.contacts.CompanyInfo.d');
INSERT INTO `sl_menu` VALUES ('11', '所有联系人', '2', null, null, '2', 'com.bstek.dorado.sample.standardlesson.junior.contacts.AllContacts.d');
INSERT INTO `sl_menu` VALUES ('12', '部门联系人', '2', null, null, '3', 'com.bstek.dorado.sample.standardlesson.middle.constacts.DeptEmployeeQuery.d');
INSERT INTO `sl_menu` VALUES ('13', '企业基础数据维护', '3', null, null, '1', 'com.bstek.dorado.sample.standardlesson.junior.base.CompanyManage.d');
INSERT INTO `sl_menu` VALUES ('14', '员工基础数据维护', '3', null, null, '2', 'com.bstek.dorado.sample.standardlesson.middle.base.EmployeeManageForFileUpload.d');
INSERT INTO `sl_menu` VALUES ('15', '主菜单管理', '3', null, null, '3', 'com.bstek.dorado.sample.standardlesson.middle.base.MenuManage.d');
INSERT INTO `sl_menu` VALUES ('16', '我的信息', '4', null, null, '1', 'com.bstek.dorado.sample.standardlesson.middle.person.MyInfo.d');
INSERT INTO `sl_menu` VALUES ('17', '我的消息', '4', null, null, '2', 'com.bstek.dorado.sample.standardlesson.middle.person.MyMessage.d');
INSERT INTO `sl_menu` VALUES ('18', '消息流水', '5', null, null, '1', 'com.bstek.dorado.sample.standardlesson.middle.message.AllMessage.d');
 
-- ----------------------------
-- Table structure for sl_message
-- ----------------------------
DROP TABLE IF EXISTS `sl_message`;
CREATE TABLE `sl_message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `receiver` varchar(45) DEFAULT NULL,
  `receiver_mail` varchar(45) DEFAULT NULL,
  `sender` int(11) DEFAULT NULL,
  `sender_mail` varchar(45) DEFAULT NULL,
  `send_date` date DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  KEY `fk_sender_id` (`sender`),
  CONSTRAINT `fk_sender_id` FOREIGN KEY (`sender`) REFERENCES `sl_employee` (`EMPLOYEE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of sl_message
-- ----------------------------
INSERT INTO `sl_message` VALUES ('16', 'admin', 'admin@codeman.cn', '1', null, '2014-02-05', '安林的手机更改为13103076337');
INSERT INTO `sl_message` VALUES ('17', 'admin', 'admin@codeman.cn', '1', null, '2014-02-15', '本人于今日去北京出差，工作相关事宜交接给白小波');