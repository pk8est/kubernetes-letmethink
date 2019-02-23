DROP TABLE IF EXISTS `lmt_cluster`;
CREATE TABLE `lmt_cluster` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `cluster_master_url` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `cluster_username` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `cluster_cert_data` text COMMENT '',
  `cluster_cert_key` text COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` varchar(50) NOT NULL DEFAULT 1 COMMENT '',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `lmt_namespace`;
CREATE TABLE `lmt_namespace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cluster_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '集群ID',
  `name` varchar(100) NOT NULL DEFAULT "" COMMENT '所属业务',
  `alias` varchar(255)  NOT NULL DEFAULT '' COMMENT '应用别名',
  `yaml` text COMMENT 'yaml/json',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` varchar(50) NOT NULL DEFAULT 1 COMMENT '',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `deploy_app`;
CREATE TABLE `deploy_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_id` varchar(100) NOT NULL DEFAULT "" COMMENT '所属业务',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '应用名',
  `alias` varchar(255)  NOT NULL DEFAULT '' COMMENT '应用别名',
  `lang` varchar(100)  NOT NULL DEFAULT '' COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `business_id` (`business_id`),
  KEY `updated_at` (`updated_at`)
) ENGINE=InnoDB COMMENT='应用表';

DROP TABLE IF EXISTS `deploy_group`;
CREATE TABLE `deploy_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `room_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '分组名',
  `alias` varchar(255)  NOT NULL DEFAULT '' COMMENT '分组别名',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`),
  KEY `room_id` (`room_id`),
  KEY `updated_at` (`updated_at`)
) ENGINE=InnoDB COMMENT='应用分组表';

DROP TABLE IF EXISTS `deploy_deploy`;
CREATE TABLE `deploy_deploy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `group_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '应用名',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`),
  KEY `group_id` (`group_id`),
  KEY `updated_at` (`updated_at`)
) ENGINE=InnoDB COMMENT='部署表';

DROP TABLE IF EXISTS `deploy_deploy_version`;
CREATE TABLE `deploy_deploy_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `deploy_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `group_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '应用名',
  `container_standard` varchar(100) NOT NULL DEFAULT '' COMMENT '容器规格',
  `disk_type` varchar(100) NOT NULL DEFAULT '' COMMENT '磁盘类型',
  `disk_size` varchar(20) NOT NULL DEFAULT '' COMMENT '磁盘大小',
  `image` varchar(255) NOT NULL DEFAULT '' COMMENT '镜像',
  `env` text COMMENT '环境变量',
  `config_map` text COMMENT '配置文件',
  `version` varchar(100) NOT NULL DEFAULT '' COMMENT '版本号',
  `version_description` varchar(1024) NOT NULL DEFAULT '' COMMENT '版本说明',
  `replicas` int(10) NOT NULL DEFAULT 0 COMMENT '副本数',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态',
  `deploy_status` varchar(50) NOT NULL DEFAULT 'UNPUBLISHED' COMMENT '发布状态',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`),
  KEY `deploy_id` (`deploy_id`),
  KEY `group_id` (`group_id`),
  KEY `updated_at` (`updated_at`)
) ENGINE=InnoDB COMMENT='部署版本表';

DROP TABLE IF EXISTS `deploy_loadbalance`;
CREATE TABLE `deploy_loadbalance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '应用名',
  `room_groups` varchar(255) NOT NULL DEFAULT '' COMMENT '机房分组',
  `domain` varchar(255) NOT NULL DEFAULT '' COMMENT '域名',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '专区类型',
  `protocol` varchar(50) NOT NULL DEFAULT '' COMMENT '协议',
  `certificate` varchar(50) NOT NULL DEFAULT '' COMMENT '协议证书',
  `schedule` varchar(50) NOT NULL DEFAULT '' COMMENT '调度算法',
  `port` int(6) NOT NULL DEFAULT 80 COMMENT '源端口',
  `health_check` varchar(255) NOT NULL DEFAULT '' COMMENT '健康检查',
  `health_value` varchar(255) NOT NULL DEFAULT '' COMMENT '检查值',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`),
  KEY `updated_at` (`updated_at`)
) ENGINE=InnoDB COMMENT='负载均衡';

DROP TABLE IF EXISTS `deploy_deploy_history`;
CREATE TABLE `deploy_deploy_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `group_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `deploy_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `version_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '',
  `log` text NOT NULL COMMENT '',
  `operate_type` varchar(50) NOT NULL DEFAULT '' COMMENT '',
  `operator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `operator_ip` varchar(50) NOT NULL DEFAULT '' COMMENT '',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`),
  KEY `group_id` (`group_id`),
  KEY `deploy_id` (`deploy_id`),
  KEY `version_id` (`version_id`),
  KEY `created_at` (`created_at`)
) ENGINE=InnoDB COMMENT='部署历史表';

DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `udb` varchar(100) NOT NULL DEFAULT '' COMMENT '',
  `server_ip` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `client_ip` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `session_id` varchar(100) NOT NULL DEFAULT '' COMMENT '',
  `request_id` varchar(100) NOT NULL DEFAULT '' COMMENT '',
  `type` varchar(100) NOT NULL DEFAULT '' COMMENT '类型',
  `title` varchar(255)  NOT NULL DEFAULT '' COMMENT '',
  `request_uri` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `request_method` varchar(40) NOT NULL DEFAULT '' COMMENT '',
  `request_content` longtext,
  `request_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `response_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `response_state` int(11) NOT NULL DEFAULT 0 COMMENT '',
  `response_code` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `response_message` longtext COMMENT '',
  `response_content` longtext,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `udb` (`udb`),
  KEY `server_ip` (`server_ip`),
  KEY `session_id` (`session_id`),
  KEY `type` (`type`),
  KEY `created_at` (`created_at`),
  KEY `client_ip` (`client_ip`)
) ENGINE=MyISAM COMMENT='统计日志表';

DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `table_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `pid_path` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `title` varchar(255)  NOT NULL DEFAULT '' COMMENT '',
  `log` text NOT NULL COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '',
  `operate_type` varchar(50) NOT NULL DEFAULT '' COMMENT '',
  `operator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `operator_ip` varchar(50) NOT NULL DEFAULT '' COMMENT '',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `table` (`table`),
  KEY `table_id` (`table_id`),
  KEY `pid_path` (`pid_path`)
) ENGINE=InnoDB COMMENT='操作日志表';

DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '中文名',
  `uid` bigint(20) NOT NULL DEFAULT 0 COMMENT 'UID',
  `udb` varchar(100) NOT NULL DEFAULT '' COMMENT '用户UDB',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否停用，1是正常，0是停用',
  `vip` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否超级管理员，0是非，1是',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `system_permission_role`;
CREATE TABLE `system_permission_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `group_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `description` text COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='角色表';

DROP TABLE IF EXISTS `system_permission_assignment`;
CREATE TABLE `system_permission_assignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `group_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `assignment_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `by_assignment_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `action` varchar(50) NOT NULL DEFAULT '' COMMENT '类型allow/exclude',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text COMMENT '',
  PRIMARY KEY (`id`),
  KEY `assignment_id` (`assignment_id`),
  KEY `by_assignment_id` (`by_assignment_id`),
  KEY `type` (`type`)
) ENGINE=InnoDB COMMENT='权限分配表';

DROP TABLE IF EXISTS `system_permission`;
CREATE TABLE `system_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `app_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `group_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `permission` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text COMMENT '',
  PRIMARY KEY (`id`),
  KEY `permission` (`permission`)
) ENGINE=InnoDB COMMENT='权限表';

