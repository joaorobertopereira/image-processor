CREATE TABLE `sis_cassino_jogos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `insert` timestamp NULL DEFAULT NULL,
  `update` timestamp NULL DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `fornecedor` varchar(255) DEFAULT NULL,
  `provedor` varchar(255) DEFAULT NULL,
  `gameid` varchar(255) NOT NULL COMMENT 'ID do jogo no fornecedor.',
  `nome` varchar(255) NOT NULL COMMENT 'Nome do jogo.',
  `type` varchar(255) DEFAULT NULL,
  `demo` int NOT NULL DEFAULT '0' COMMENT 'Se o jogo tem a versão demo.',
  `mobile` int NOT NULL DEFAULT '0' COMMENT 'Se o jogo tem é mobile.',
  `aovivo` int NOT NULL DEFAULT '0' COMMENT 'Se o jogo é ao vivo.',
  `lobby` int NOT NULL DEFAULT '0' COMMENT 'Se o jogo tem lobby data.',
  `aspectratio` varchar(255) DEFAULT NULL,
  `imagem_horizontal` varchar(255) DEFAULT NULL,
  `imagem_quadrada` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL COMMENT 'Categoria do jogo.',
  `type_import` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `tag_color` varchar(255) DEFAULT NULL,
  `tag_type` varchar(255) DEFAULT NULL,
  `tema` varchar(255) NOT NULL DEFAULT '' COMMENT 'Tema do Jogo',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_gameid_fornecedor` (`gameid`,`fornecedor`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8442 DEFAULT CHARSET=utf8mb3;