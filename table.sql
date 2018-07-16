
-- 创建库
CREATE DATABASE admin_parent;

-- 菜单表及初始数据
CREATE TABLE `system_admin_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `link` varchar(255) DEFAULT NULL COMMENT '链接',
  `is_journal` int(11) NOT NULL DEFAULT '0' COMMENT '是否要记录日志：0否，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单表';


INSERT INTO `system_admin_menu` (`id`, `parent_id`, `name`, `link`, `is_journal`)
VALUES
	(1,0,'系统管理','',1),
	(2,1,'菜单管理','/system/adminmenu/list',1),
	(3,1,'角色管理','/system/roleinfo/list',1),
	(27,1,'用户管理','/system/userinfo/list',1),
	(41,1,'数据字典','/system/dictionary/list',1),
	(88,27,'添加用户页','/system/userinfo/toadd',1),
	(89,27,'编辑用户页','/system/userinfo/toedit',1),
	(90,27,'删除用户','/system/userinfo/delete',1),
	(93,2,'添加菜单页','/system/adminmenu/toadd',1),
	(94,2,'修改菜单页','/system/adminmenu/toedit',1),
	(95,2,'删除菜单','/system/adminmenu/delete',1),
	(96,3,'添加角色页','/system/roleinfo/toadd',1),
	(97,3,'修改角色页','/system/roleinfo/toedit',1),
	(98,3,'删除角色','/system/roleinfo/delete',1),
	(99,41,'添加字典页','/system/dictionary/toadd',1),
	(100,41,'修改字典页','/system/dictionary/toedit',1),
	(101,41,'删除字典','/system/dictionary/delete',1),
	(143,1,'日志管理','/system/journal/list',1),
	(152,143,'日志详情','/system/journal/todetail',1),
	(155,93,'新增菜单信息','/system/adminmenu/add',1),
	(156,94,'修改菜单信息','/system/adminmenu/edit',1),
	(158,96,'新增角色信息','/system/roleinfo/add',1),
	(159,97,'修改角色信息','/system/roleinfo/edit',1),
	(160,88,'新增用户信息','/system/userinfo/add',1),
	(161,89,'修改用户信息','/system/userinfo/edit',1),
	(162,100,'修改字典信息','/system/dictionary/edit',1),
	(163,99,'添加字典信息','/system/dictionary/add',1);


-- 数据字典表及初始数据
CREATE TABLE `system_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_key` varchar(50) DEFAULT NULL COMMENT '字典key',
  `dict_value` varchar(200) DEFAULT NULL COMMENT '字典vlue',
  `comments` varchar(50) DEFAULT '' COMMENT '注释',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典表';


INSERT INTO `system_dictionary` (`id`, `dict_key`, `dict_value`, `comments`, `create_time`)
VALUES
	(1,'SYSTEM_QQ','123456','管理员QQ',NULL);


-- 日志表
CREATE TABLE `system_operate_journal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `journal_type` int(11) DEFAULT NULL COMMENT '日志类型：1操作日志,2异常日志',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `user_mobile` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `operate_name` varchar(50) DEFAULT NULL COMMENT '操作名称',
  `ip_addr` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `request_path` longtext COMMENT '请求路径',
  `request_parameter` longtext COMMENT '请求参数',
  `exception_journal` longtext COMMENT '异常记录',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统操作日志表';


-- 角色权限表
CREATE TABLE `system_role_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `menu_ids` longtext NOT NULL COMMENT '菜单ids',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统权限';

INSERT INTO `system_role_info` (`id`, `parent_id`, `role_name`, `menu_ids`, `create_time`, `description`)
VALUES
	(1, 0, '开发者', '1,143,152,41,101,100,162,99,163,27,90,89,161,88,160,3,98,97,159,96,158,2,95,94,156,93,155', '2017-01-12 17:12:32', '项目开发人员'),
	(12, 1, '管理员', '1,27,90,89,161,88,160,3,98,97,159,96,158', '2018-07-16 09:00:30', '软件管理员');


-- 用户信息表
CREATE TABLE `system_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(50) DEFAULT NULL COMMENT '中文名',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户登录名',
  `user_email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `user_mobile` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户登录密码',
  `login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次登录时间',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `role_id` int(11) DEFAULT NULL COMMENT '所属角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

INSERT INTO `system_user_info` (`id`, `real_name`, `user_name`, `user_email`, `user_mobile`, `user_pwd`, `login_time`, `creator`, `create_time`, `role_id`)
VALUES
	(1,'Psuso',NULL,NULL,'13262872357','f3ae97b6a5b80038db24ad428b594d03','2018-07-16 15:48:05',0,'2016-12-15 10:31:52',1);


