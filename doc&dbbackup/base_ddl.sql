USE [xg]
GO

/****** Object: Table [dbo].[BASE_FIELDS]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[BASE_FIELDS]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[BASE_FIELDS]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BASE_FIELDS] (
	[FIELD_ID] varchar(50) NOT NULL,
	[FIELD] varchar(64) NULL,
	[FIELD_NAME] varchar(128) NULL,
	[VALUE_FIELD] varchar(128) NULL,
	[DISPLAY_FIELD] varchar(128) NULL,
	[ENABLED] int NULL,
	[SORT] int NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_FIELDS]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_FIELDS]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO

/****** Object: Table [dbo].[BASE_MODULES]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[BASE_MODULES]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[BASE_MODULES]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BASE_MODULES] (
	[MODULE_ID] int NOT NULL,
	[MODULE_NAME] varchar(64) NOT NULL,
	[MODULE_URL] varchar(64) NULL,
	[PARENT_ID] int NULL,
	[LEAF] int NULL,
	[EXPANDED] int NULL,
	[DISPLAY_INDEX] int NULL,
	[IS_DISPLAY] int NULL,
	[EN_MODULE_NAME] varchar(64) NULL,
	[ICON_CSS] varchar(128) NULL,
	[INFORMATION] varchar(128) NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_MODULES]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_MODULES]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO

/****** Object: Table [dbo].[BASE_ROLE_MODULE]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[BASE_ROLE_MODULE]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[BASE_ROLE_MODULE]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BASE_ROLE_MODULE] (
	[ROLE_MODULE_ID] varchar(50) NOT NULL,
	[ROLE_ID] varchar(50) NOT NULL,
	[MODULE_ID] int NOT NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_ROLE_MODULE]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_ROLE_MODULE]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO

/****** Object: Table [dbo].[BASE_ROLES]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[BASE_ROLES]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[BASE_ROLES]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BASE_ROLES] (
	[ROLE_ID] varchar(50) NOT NULL,
	[ROLE_NAME] varchar(64) NULL,
	[ROLE_DESC] varchar(128) NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_ROLES]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_ROLES]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO

/****** Object: Table [dbo].[BASE_USER_ROLE]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[BASE_USER_ROLE]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[BASE_USER_ROLE]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BASE_USER_ROLE] (
	[USER_ROLE_ID] varchar(50) NOT NULL,
	[USER_ID] varchar(50) NOT NULL,
	[ROLE_ID] varchar(50) NOT NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_USER_ROLE]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_USER_ROLE]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO

/****** Object: Table [dbo].[BASE_USERS]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[BASE_USERS]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[BASE_USERS]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BASE_USERS] (
	[USER_ID] varchar(50) NOT NULL,
	[ACCOUNT] varchar(64) NOT NULL,
	[PASSWORD] varchar(128) NOT NULL,
	[REAL_NAME] varchar(64) NULL,
	[SEX] int NULL,
	[EMAIL] varchar(64) NULL,
	[MOBILE] varchar(32) NULL,
	[OFFICE_PHONE] varchar(32) NULL,
	[ERROR_COUNT] int NULL CONSTRAINT [D_dbo_BASE_USERS_1] DEFAULT ((0)),
	[LAST_LOGIN_TIME] datetime NULL,
	[LAST_LOGIN_IP] varchar(32) NULL,
	[REMARK] varchar(128) NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_USERS]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[BASE_USERS]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO

/****** Object: Table [dbo].[WEB_MESSAGE]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[WEB_MESSAGE]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[WEB_MESSAGE]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[WEB_MESSAGE] (
	[ID] varchar(100) NOT NULL,
	[HEAD] varchar(100) NULL,
	[BODY] varchar(500) NULL,
	[BEGIN_DATE] datetime NULL,
	[END_DATE] datetime NULL,
	[MXDX] varchar(100) NULL,
	[RELEASE_TIME] datetime NULL,
	[RELEASE_IP] varchar(100) NULL,
	[RELEASE_PER] varchar(100) NULL,
	[ISDISPLAY] varchar(100) NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[WEB_MESSAGE]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[WEB_MESSAGE]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO

/****** Object: Table [dbo].[WEB_MESSAGE_USER]   Script Date: 2013/10/17 9:40:42 ******/
GO

IF (EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[WEB_MESSAGE_USER]') AND type ='U'))
BEGIN
	DROP TABLE [dbo].[WEB_MESSAGE_USER]
END

GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[WEB_MESSAGE_USER] (
	[ID] varchar(100) NOT NULL,
	[MESSAGE_ID] varchar(100) NULL,
	[USER_ID] varchar(100) NULL
) ON [PRIMARY]
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[WEB_MESSAGE_USER]',
	@OptionName  = 'table lock on bulk load',
	@OptionValue  = 'OFF'
GO

EXECUTE [sp_tableoption]
	@TableNamePattern  = N'[dbo].[WEB_MESSAGE_USER]',
	@OptionName  = 'vardecimal storage format',
	@OptionValue  = 'OFF'
GO