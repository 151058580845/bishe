-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2020-05-22 02:08:15
-- 服务器版本： 10.4.6-MariaDB
-- PHP 版本： 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `智慧社区`
--

-- --------------------------------------------------------

--
-- 表的结构 `tb_address`
--

CREATE TABLE `tb_address` (
  `room_id` int(11) NOT NULL,
  `address` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tingshi` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `money` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `chaoxiang` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `has` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `tb_address`
--

INSERT INTO `tb_address` (`room_id`, `address`, `tingshi`, `money`, `chaoxiang`, `has`) VALUES
(0, '1111小区1111幢0000室', '三室一厅', '500万', '东', 1),
(1, '1111小区1111幢1111室', '三室一厅', '300万', '南', 0),
(2, '1111小区2222幢2222室', '三室一厅', '500万', '西', 0),
(3, '1111小区3333幢3333室', '三室一厅', '400万', '北', 0),
(4, '1111小区4444幢4444室', '三室一厅', '500万', '南', 0),
(5, '1111小区5555幢5555室', '三室二厅', '400万', '南', 0),
(6, '1111小区6666幢6666室', '三室一厅', '500万', '西', 0),
(7, '1111小区7777幢7777室', '三室二厅', '700万', '东', 0),
(8, '1111小区8888幢8888室', '二室一厅', '500万', '南', 0),
(9, '1111小区9999幢9999室', '三室一厅', '500万', '南', 0);

-- --------------------------------------------------------

--
-- 表的结构 `tb_chengshi`
--

CREATE TABLE `tb_chengshi` (
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `tb_chuzu`
--

CREATE TABLE `tb_chuzu` (
  `room_id` bigint(11) NOT NULL,
  `zujin` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `introduce` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `tingshi` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `fangshi` varchar(10) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `chaoxiang` varchar(10) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `look` bigint(255) NOT NULL DEFAULT 0,
  `user_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_chuzu`
--

INSERT INTO `tb_chuzu` (`room_id`, `zujin`, `time`, `address`, `introduce`, `tingshi`, `fangshi`, `chaoxiang`, `phone`, `look`, `user_id`) VALUES
(1, '123', '2020-05-21', 'xxxx小区xx栋xx单元xxxx室', '123', '三室二厅', '整租', '东', '15105850845', 0, 1),
(9, '123', '2020-05-21', '1111小区9999幢9999室', '123', '三室一厅', 'null', '南', '323123213', 0, 1),
(103, '1000', '2020-05-21', '2222小区2222幢2222室', '3213', '一室一厅', 'null', '南', '12312312312', 0, 4),
(0, '1010', '2020-05-21', '1111小区1111幢0000室', '321321', '三室一厅', 'null', '东', '32132132132', 0, 4),
(100, '123', '2020-05-21', 'xxxx小区xx栋xx单元xxxx室', '123', '三室二厅', 'null', '东', '123', 0, 1);

-- --------------------------------------------------------

--
-- 表的结构 `tb_fuwu`
--

CREATE TABLE `tb_fuwu` (
  `danhao` bigint(11) NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `weixiuren` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0',
  `success_time` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_fuwu`
--

INSERT INTO `tb_fuwu` (`danhao`, `phone`, `time`, `name`, `weixiuren`, `success_time`) VALUES
(39, '15105850845', '2020-05-20 04:05:27', '报纸', '0', '0'),
(38, '15105850845', '2020-05-20 02:05:27', '牛奶', '0', '0'),
(37, '17395737723', '2020-05-07 02:05:51', '报纸', '0', '0'),
(36, '17395737723', '2020-05-07 02:05:48', '牛奶', '0', '0'),
(35, '15105850845', '2020-04-26 03:04:16', '报纸', '0', '0'),
(34, '15105850845', '2020-04-26', '报纸', '0', '0'),
(33, '15105850845', '2020-04-26', '牛奶', '0', '0'),
(32, '15105850845', '2020-04-05', '桶装水', '0', '0'),
(40, '15105850845', '2020-05-20 05:05:05', '报纸', '0', '0'),
(41, '173957377', '2020-05-21 06:05:54', '牛奶', '0', '0'),
(42, '15105850845', '2020-05-21 07:05:13', '报纸', '0', '0');

-- --------------------------------------------------------

--
-- 表的结构 `tb_fuwu_list`
--

CREATE TABLE `tb_fuwu_list` (
  `id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `money` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `num` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_fuwu_list`
--

INSERT INTO `tb_fuwu_list` (`id`, `name`, `money`, `num`) VALUES
(1, '桶装水', '10', 1),
(2, '报纸', '20', 0),
(3, '牛奶', '30', 0);

-- --------------------------------------------------------

--
-- 表的结构 `tb_jiaofei`
--

CREATE TABLE `tb_jiaofei` (
  `id` bigint(20) NOT NULL,
  `dian` int(11) NOT NULL,
  `shui` int(11) NOT NULL,
  `wuye` int(11) NOT NULL,
  `time` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `caozuo` int(11) DEFAULT 1
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_jiaofei`
--

INSERT INTO `tb_jiaofei` (`id`, `dian`, `shui`, `wuye`, `time`, `caozuo`) VALUES
(1, 0, 123, 132, '2020-05-29', 1),
(2, 111, 121, 131, '2020-05-29', 1),
(3, 212, 222, 2232, '2020-05-29', 1),
(4, 222, 222, 222, '2020-04-29', 0);

-- --------------------------------------------------------

--
-- 表的结构 `tb_user`
--

CREATE TABLE `tb_user` (
  `id` bigint(10) NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_user`
--

INSERT INTO `tb_user` (`id`, `phone`, `name`, `password`) VALUES
(1, '15105850845', '小竺', '123456'),
(4, '173957377', '小刘', '111'),
(3, '12', '小鸭', '12'),
(2, '123456', '小王', '123456'),
(14, '17395737723', '123', '123123');

-- --------------------------------------------------------

--
-- 表的结构 `tb_user_address`
--

CREATE TABLE `tb_user_address` (
  `room_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `address` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tingshi` varchar(30) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `money` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `chaoxiang` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `is_chuzu` int(11) NOT NULL DEFAULT 0,
  `fangshi` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_user_address`
--

INSERT INTO `tb_user_address` (`room_id`, `id`, `address`, `tingshi`, `money`, `chaoxiang`, `is_chuzu`, `fangshi`) VALUES
(101, 1, 'xxxx小区xx栋xx单元xxxx室', '三室二厅', '500万', '东', 1, '整租'),
(102, 1, '1111小区11栋11单元1111室', '二室二厅', '260万', '西', 0, '合租'),
(103, 4, '2222小区2222幢2222室', '一室一厅', '300万', '南', 1, NULL),
(100, 1, 'xxxx小区xx栋xx单元xxxx室', '三室二厅', '500万', '东', 1, NULL),
(0, 4, '1111小区1111幢0000室', '三室一厅', '500万', '东', 1, NULL),
(104, 1, '1111小区9999幢9999室', '三室一厅', '500万', '南', 1, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `tb_user_info`
--

CREATE TABLE `tb_user_info` (
  `id` int(11) NOT NULL,
  `name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `sex` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `height` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `weight` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `birth` varchar(30) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_user_info`
--

INSERT INTO `tb_user_info` (`id`, `name`, `sex`, `height`, `weight`, `birth`) VALUES
(1, 'zxj', '男', '65', '28', '2020-2-16'),
(9, '匿名用户', '女', '', '', '');

-- --------------------------------------------------------

--
-- 表的结构 `tb_user_tousu`
--

CREATE TABLE `tb_user_tousu` (
  `id` bigint(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `leibie` int(11) DEFAULT 0,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0',
  `message` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `chuli_id` bigint(20) DEFAULT 0,
  `address` varchar(10000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fankui` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_chuli` varchar(100) COLLATE utf8_unicode_ci DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_user_tousu`
--

INSERT INTO `tb_user_tousu` (`id`, `user_id`, `name`, `leibie`, `phone`, `message`, `time`, `chuli_id`, `address`, `fankui`, `is_chuli`) VALUES
(72, 1, 'xiaowang', 0, '15105850584', '123', '2020-05-20', 1, ',./img/20200520171533_IMG_20200505_213034.jpg,./img/20200520171533_IMG_20200505_213036.jpg,./img/20200520171533_IMG_20200515_23185904.png', '是倒计时的阿达是的撒的阿萨德阿萨德sad', '1'),
(73, 1, 'qwe', 0, '1233213211', '3213', '2020-05-20', 0, ',./img/20200520172142_IMG_20200505_213034.jpg,./img/20200520172142_IMG_20200515_23185904.png', NULL, '0'),
(59, 1, '22', 0, '22', '22', '2020-04-26', 1, ',./img/20200426093150_IMG_20200326_212137.jpg,./img/20200426093212_IMG_20200326_212134.jpg,./img/20200426094127_IMG_20200326_212134.jpg,./img/20200426094136_IMG_20200326_212137.jpg,./img/20200426094136_IMG_20200326_212134.jpg,./img/20200426094138_IMG_20200326_212137.jpg,./img/20200426094138_IMG_20200326_212134.jpg', '', '0'),
(58, 1, '123', 0, '321', '321', '2020-04-26', 1, ',./img/20200426075548_IMG_20200326_212134.jpg,./img/20200426092957_IMG_20200423_01552215.jpg,./img/20200426092957_IMG_20200326_212137.jpg,./img/20200426093038_IMG_20200326_212134.jpg', '', '0'),
(60, 1, '匿名用户', 0, '123', '312', '2020-05-16', 1, ',./img/20200516052046_IMG_20200505_213034.jpg,./img/20200516052101_IMG_20200505_213034.jpg,./img/20200516052101_IMG_20200505_213036.jpg', '', '0'),
(74, 1, '123', 0, '123', '123', '2020-05-21', 0, ',./img/20200521034431_IMG_20200505_213034.jpg,./img/20200521034431_IMG_20200505_213036.jpg', NULL, '0'),
(75, 4, '123', 2, '12312312312', '123123', '2020-05-21', 0, ',./img/20200521072919_IMG_20200505_213034.jpg,./img/20200521072919_IMG_20200505_213036.jpg', NULL, '0');

-- --------------------------------------------------------

--
-- 表的结构 `tb_weixiu`
--

CREATE TABLE `tb_weixiu` (
  `danhao` bigint(11) NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `jieguo` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0',
  `juti` varchar(100) COLLATE utf8_unicode_ci DEFAULT '0',
  `weixiuren` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0',
  `success_time` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_weixiu`
--

INSERT INTO `tb_weixiu` (`danhao`, `phone`, `time`, `name`, `jieguo`, `juti`, `weixiuren`, `success_time`) VALUES
(38, '15105850845', '2020-05-21', '你鞋子', '0', '0', '0', '0'),
(37, '15105850845', '2020-05-20', '123', '0', '0', '0', '0'),
(36, '15105850845', '2020-05-20', '1323', '维修中', '0', '小鸭', '0');

-- --------------------------------------------------------

--
-- 表的结构 `tb_weixiu_copy2`
--

CREATE TABLE `tb_weixiu_copy2` (
  `id` bigint(11) NOT NULL,
  `time` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `tousu_id` varchar(20) COLLATE utf8_unicode_ci DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `tb_work_user`
--

CREATE TABLE `tb_work_user` (
  `id` bigint(10) NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_work_user`
--

INSERT INTO `tb_work_user` (`id`, `phone`, `name`, `password`) VALUES
(1, '15105850845', '1', '123'),
(9, '17395737723', '2222', '111'),
(8, '12', '12', '12'),
(12, '', '', ''),
(11, '123', '123', '123123'),
(13, '', '', '');

-- --------------------------------------------------------

--
-- 表的结构 `tb_zhangdan`
--

CREATE TABLE `tb_zhangdan` (
  `id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `money` int(11) NOT NULL,
  `data_year` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `data_month` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `data_day` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `time` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- 转存表中的数据 `tb_zhangdan`
--

INSERT INTO `tb_zhangdan` (`id`, `type`, `money`, `data_year`, `data_month`, `data_day`, `time`, `name`) VALUES
(1, 1, 1000, '2020', '1', '14', '2020-01-14 12:51', '南湖大山阿达但是的'),
(1, 2, 100, '2020', '1', '14', '2020-01-14 12:50', '南湖大山阿达但是的'),
(1, 3, 140, '2020', '12', '11', '2020-12-1 12:24', '南湖大山阿达但是的'),
(1, 1, 100, '2020', '1', '14', '2020-01-14 12:51', '南湖大山阿达但是的'),
(1, 0, 111, '2020', '5', '12', '2020-05-03 08:19', '电费'),
(1, 1, 122, '2020', '5', '20', '2020-05-20 02:36', '都是'),
(1, 0, 111, '2020', '5', '20', '2020-05-20 05:07', '5'),
(1, 1, 1000, '2020', '1', '14', '2020-01-14 12:51', '南湖大山阿达但是的'),
(1, 2, 100, '2020', '1', '14', '2020-01-14 12:50', '南湖大山阿达但是的'),
(1, 3, 140, '2020', '12', '1', '2020-12-1 12:24', '南湖大山阿达但是的'),
(1, 1, 100, '2020', '1', '14', '2020-01-14 12:51', '南湖大山阿达但是的'),
(1, 0, 111, '2020', '05', '03', '2020-05-03 08:19', '电费'),
(1, 0, 123, '2020', '5', '21', '2020-05-21 07:24', '5');

--
-- 转储表的索引
--

--
-- 表的索引 `tb_address`
--
ALTER TABLE `tb_address`
  ADD PRIMARY KEY (`room_id`);

--
-- 表的索引 `tb_chuzu`
--
ALTER TABLE `tb_chuzu`
  ADD PRIMARY KEY (`room_id`);

--
-- 表的索引 `tb_fuwu`
--
ALTER TABLE `tb_fuwu`
  ADD PRIMARY KEY (`danhao`) USING BTREE;

--
-- 表的索引 `tb_fuwu_list`
--
ALTER TABLE `tb_fuwu_list`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- 表的索引 `tb_jiaofei`
--
ALTER TABLE `tb_jiaofei`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- 表的索引 `tb_user_address`
--
ALTER TABLE `tb_user_address`
  ADD PRIMARY KEY (`room_id`) USING BTREE;

--
-- 表的索引 `tb_user_info`
--
ALTER TABLE `tb_user_info`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- 表的索引 `tb_user_tousu`
--
ALTER TABLE `tb_user_tousu`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- 表的索引 `tb_weixiu`
--
ALTER TABLE `tb_weixiu`
  ADD PRIMARY KEY (`danhao`) USING BTREE;

--
-- 表的索引 `tb_weixiu_copy2`
--
ALTER TABLE `tb_weixiu_copy2`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- 表的索引 `tb_work_user`
--
ALTER TABLE `tb_work_user`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `tb_fuwu`
--
ALTER TABLE `tb_fuwu`
  MODIFY `danhao` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- 使用表AUTO_INCREMENT `tb_fuwu_list`
--
ALTER TABLE `tb_fuwu_list`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 使用表AUTO_INCREMENT `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `id` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- 使用表AUTO_INCREMENT `tb_user_tousu`
--
ALTER TABLE `tb_user_tousu`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- 使用表AUTO_INCREMENT `tb_weixiu`
--
ALTER TABLE `tb_weixiu`
  MODIFY `danhao` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- 使用表AUTO_INCREMENT `tb_weixiu_copy2`
--
ALTER TABLE `tb_weixiu_copy2`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- 使用表AUTO_INCREMENT `tb_work_user`
--
ALTER TABLE `tb_work_user`
  MODIFY `id` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
