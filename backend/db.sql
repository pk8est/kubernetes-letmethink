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
  KEY `cluster_id` (`cluster_id`),
  KEY `name` (`name`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `lmt_configmap_set`;
CREATE TABLE `lmt_configmap_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cluster_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `namespace_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `alias` varchar(255)  NOT NULL DEFAULT '' COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` varchar(50) NOT NULL DEFAULT 1 COMMENT '',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `namespace_id` (`namespace_id`),
  KEY `cluster_id` (`cluster_id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `lmt_configmap_group`;
CREATE TABLE `lmt_configmap_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cluster_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `namespace_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `set_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `alias` varchar(255)  NOT NULL DEFAULT '' COMMENT '',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `status` varchar(50) NOT NULL DEFAULT 1 COMMENT '',
  `delete_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
  `creator_uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `description` text COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `namespace_id` (`namespace_id`),
  KEY `set_id` (`set_id`),
  KEY `cluster_id` (`cluster_id`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `lmt_configmap`;
CREATE TABLE `lmt_configmap` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cluster_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `namespace_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `set_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `group_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '',
  `name` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `alias` varchar(100) NOT NULL DEFAULT "" COMMENT '',
  `version` varchar(100) NOT NULL DEFAULT "" COMMENT '',
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
  KEY `namespace_id` (`namespace_id`),
  KEY `set_id` (`set_id`),
  KEY `cluster_id` (`cluster_id`)
) ENGINE=InnoDB;


