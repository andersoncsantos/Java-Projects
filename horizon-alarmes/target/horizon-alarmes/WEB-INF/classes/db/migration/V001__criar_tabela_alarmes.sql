CREATE TABLE alarmes(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	site VARCHAR(50),
	data_inicio VARCHAR(50),
	data_fim VARCHAR(50),
	status VARCHAR(50),
	alarme VARCHAR(50),
	descricao VARCHAR(200),
	incidente VARCHAR(50),
	plataforma VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO alarmes VALUES (0, 'MAIARM001OU', '29/12/2018 23:23', '30/12/2018 00:00', 'FECHADO', 'NodeB Unavailable', 'Subrack No.=0, Slot No.=2, Subsystem No.=4, NodeB ID=1057, NodeB Name=MAIARM001OU, Alarm Cause=Port of the NodeB unavailable.', 'INC000000958623', 'huawei_M2000_corba');
INSERT INTO alarmes VALUES (0, 'SPICAS022OU', '29/12/2018 23:23', '30/12/2018 00:00', 'FECHADO', 'NodeB Unavailable', 'Subrack No.=0, Slot No.=2, Subsystem No.=4, NodeB ID=1057, NodeB Name=MAIARM001OU, Alarm Cause=Port of the NodeB unavailable.', 'INC000000958624', 'huawei_M2000_corba');
INSERT INTO alarmes VALUES (0, 'RJINIU022OU', '29/12/2018 23:23', '30/12/2018 00:00', 'FECHADO', 'NodeB Unavailable', 'Subrack No.=0, Slot No.=2, Subsystem No.=4, NodeB ID=1057, NodeB Name=MAIARM001OU, Alarm Cause=Port of the NodeB unavailable.', 'INC000000958625', 'huawei_M2000_corba');
INSERT INTO alarmes VALUES (0, 'SPCERM001OU', '29/12/2018 23:23', '30/12/2018 00:00', 'FECHADO', 'NodeB Unavailable', 'Subrack No.=0, Slot No.=2, Subsystem No.=4, NodeB ID=1057, NodeB Name=MAIARM001OU, Alarm Cause=Port of the NodeB unavailable.', 'INC000000958626', 'huawei_M2000_corba');
INSERT INTO alarmes VALUES (0, 'SPCMOO025OU', '29/12/2018 23:23', '30/12/2018 00:00', 'FECHADO', 'NodeB Unavailable', 'Subrack No.=0, Slot No.=2, Subsystem No.=4, NodeB ID=1057, NodeB Name=MAIARM001OU, Alarm Cause=Port of the NodeB unavailable.', 'INC000000958627', 'huawei_M2000_corba');