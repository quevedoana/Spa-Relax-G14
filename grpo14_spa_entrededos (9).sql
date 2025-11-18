-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-11-2025 a las 02:27:34
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `grpo14_spa_entrededos`
--
CREATE DATABASE IF NOT EXISTS `grpo14_spa_entrededos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `grpo14_spa_entrededos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `codCli` int(11) NOT NULL,
  `DNI` int(11) NOT NULL,
  `NombreCompleto` varchar(60) NOT NULL,
  `Telefono` bigint(20) NOT NULL,
  `Edad` int(11) NOT NULL,
  `Afecciones` varchar(30) NOT NULL,
  `Estado` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`codCli`, `DNI`, `NombreCompleto`, `Telefono`, `Edad`, `Afecciones`, `Estado`) VALUES
(22, 44075900, 'Assat Antonio Tomas', 26645687, 23, 'Demasiado sano', 0),
(23, 39137807, 'di Fiore Mariano Enzo', 26645687, 15, 'Mucha facha', 1),
(24, 45886496, 'Barroso Esteban Jose', 26645609, 21, 'Mucho counter', 1),
(25, 43343200, 'Ana Paula Quevedo', 26647324, 25, 'Mucha lokura', 1),
(37, 4545454, 'Candela Naranjo', 2665121235, 21, 'nada', 1),
(38, 17562145, 'Martin Marquez', 2657452133, 55, 'Alergico Aloe Vera', 1),
(39, 39456785, 'Thiago Torres', 2185235478, 30, 'nada', 1),
(40, 37541268, 'Rodrigo Rodriguez', 2657451236, 32, 'Alergico a Perros', 1),
(41, 19542568, 'Fernando Fernandez', 26574125687, 40, 'Alergico a Mujeres', 1),
(42, 40651236, 'Ricardo Rivero', 2657412587, 28, 'Alergico a polvo', 1),
(43, 44568951, 'Valentin Vergara', 2665123547, 25, 'Alergico a gatos', 1),
(44, 45157489, 'Juan Jose Lopez', 2665478122, 26, 'Alergico a insectos', 1),
(45, 57125478, 'Andrea Ardiles', 2664875126, 15, 'Nada', 1),
(46, 17458965, 'Valentina Vergara', 2665894512, 53, 'Alergico a piedras', 1),
(47, 38452159, 'Ricardo Roberto Ruiz', 2657415874, 31, 'Alergico a Mala Onda', 1),
(48, 50425698, 'Mario Molina', 2665203269, 20, 'alergico al laburito', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consultorio`
--

CREATE TABLE `consultorio` (
  `nroConsultorio` int(11) NOT NULL,
  `usos` varchar(40) NOT NULL,
  `equipamiento` varchar(60) NOT NULL,
  `apto` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consultorio`
--

INSERT INTO `consultorio` (`nroConsultorio`, `usos`, `equipamiento`, `apto`) VALUES
(1, 'Corporal', 'Camilla de tratamiento regulable, Ropa de camilla, sábanas d', 1),
(2, 'Relajacion', 'Calentador de Mesa de Masaje, Difusor de aromaterapia', 1),
(3, 'Facial', 'Mesa de Tratamiento Facial, Máquina multifunción facial (ste', 1),
(4, 'Estetico', 'Camilla, frascos para desinfectantes, esterilizador', 1),
(6, 'Masajes', 'Camilla, Aceites, Batas, Piedras calientes', 0),
(7, 'Depilacion', 'Camilla, Maquina depiladora, Aceites, Crema, Bata', 1),
(8, 'Corporal', 'Piedras Calientes, Sauna, Bata, Difusor de aromaterapia', 1),
(9, 'Facial', 'Sillon, Mascarillas, Vaporizadores', 1),
(10, 'Masajes', 'Camilla, Bata, Sillon Masajeador, Piedras Calientes, Aceites', 1),
(11, 'Depilacion', 'Camilla, Aceites, Cremas, Maruina de Depilar', 1),
(12, 'Facial', 'Mascarillas, Sillon, Cremas Humectantes, Maquina de aromater', 1),
(13, 'Corporal', 'Camilla, Agujas de Acupuntura', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dia_de_spa`
--

CREATE TABLE `dia_de_spa` (
  `codPack` int(11) NOT NULL,
  `fechaYHora` datetime NOT NULL,
  `preferencias` varchar(40) NOT NULL,
  `codCli` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `sesiones` int(11) DEFAULT NULL,
  `monto` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dia_de_spa`
--

INSERT INTO `dia_de_spa` (`codPack`, `fechaYHora`, `preferencias`, `codCli`, `estado`, `sesiones`, `monto`) VALUES
(10, '2025-11-20 13:00:00', 'masajes', 25, 1, NULL, 105500),
(14, '2025-11-28 18:00:00', 'masajes', 25, 1, NULL, 57500),
(17, '2025-11-08 21:33:00', 'nada', 22, 1, NULL, 0),
(18, '2025-11-28 09:00:00', 'masajes', 25, 1, NULL, 65000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialista`
--

CREATE TABLE `especialista` (
  `matricula` varchar(10) NOT NULL,
  `NombreYApellido` varchar(60) NOT NULL,
  `telefono` bigint(11) NOT NULL,
  `especialidad` varchar(40) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `especialista`
--

INSERT INTO `especialista` (`matricula`, `NombreYApellido`, `telefono`, `especialidad`, `estado`) VALUES
('AB1', 'Agustin Diaz ', 214748361, 'Facial', 1),
('AB123', 'Antonio Assat', 214748364, 'Corporal', 1),
('AB1234', 'Mariano di Fiore', 266456789, 'Facial', 1),
('AB12343', 'Esteban Barroso', 214748364, 'Estético', 1),
('AB12345', 'Ana Banana', 214748364, 'Relajación', 1),
('AB4321', 'Candela Naranjo', 266458970, 'Facial', 1),
('AB5463', 'Jose Lopez', 214748364, 'Estético', 1),
('AB55124', 'Andres Amaya', 214748364, 'Corporal', 1),
('AB5712', 'Diego Dominguez', 266457891, 'Relajación', 1),
('AB5745', 'Gustavo Santaolalla', 214748364, 'Relajación', 0),
('AB5786', 'Carlos Cortez', 214748364, 'Corporal', 1),
('CD1418', 'Fabian Ferrero', 214748364, 'Estético', 1),
('FD5730', 'Gaston Gimenez', 214748364, 'Facial', 1),
('GH5412', 'Hugo Hidalgo', 217483647, 'Relajación', 1),
('GH6214', 'Ignacio Iglesias', 112546785, 'Corporal', 1),
('JJ4521', 'Javier Juearez', 214743647, 'Corporal', 1),
('JR1354', 'Lautaro Lagos', 114523658, 'Estético', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instalacion`
--

CREATE TABLE `instalacion` (
  `codInstal` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `detalleDeUso` varchar(60) NOT NULL,
  `precio30m` double NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `instalacion`
--

INSERT INTO `instalacion` (`codInstal`, `nombre`, `detalleDeUso`, `precio30m`, `estado`) VALUES
(1, 'Piscina Climatizada', 'Piscinas con agua a temperatura agradable, a veces equipadas', 20000, 0),
(3, 'Hidromasaje', 'Bañeras con chorros de agua a presión para relajar los músc', 15000, 1),
(4, 'Sauna', 'Habitaciones con vapor de agua caliente que ayudan a purifi', 20000, 1),
(5, 'Duchas de sensaciones', 'Combina diferentes temperaturas, presiones y aromas para est', 10000, 1),
(6, 'Pediluvio', 'Baño de pies que generalmente combina agua fría y caliente p', 10000, 1),
(7, 'Circuito de hidroterapia', 'Secuencia de 3 bañeras de agua con diferentes temperaturas p', 25000, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sesion`
--

CREATE TABLE `sesion` (
  `codSesion` int(11) NOT NULL,
  `fechaYHoraInicio` datetime NOT NULL,
  `fechaYHoraFin` datetime NOT NULL,
  `codTratamiento` int(11) NOT NULL,
  `nroConsultorio` int(11) DEFAULT NULL,
  `matriculaMasajista` varchar(10) NOT NULL,
  `CodInstalacion` int(11) DEFAULT NULL,
  `codPack` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `sesion`
--

INSERT INTO `sesion` (`codSesion`, `fechaYHoraInicio`, `fechaYHoraFin`, `codTratamiento`, `nroConsultorio`, `matriculaMasajista`, `CodInstalacion`, `codPack`, `estado`) VALUES
(16, '2025-11-17 12:00:00', '2025-11-17 13:10:00', 7, 3, 'AB1', 3, 14, 1),
(17, '2025-11-17 15:00:00', '2025-11-17 16:10:00', 10, 3, 'AB4321', 5, 14, 1),
(18, '2025-11-17 14:00:00', '2025-11-17 15:10:00', 7, 3, 'AB4321', 4, 14, 1),
(19, '2025-11-17 17:00:00', '2025-11-17 17:55:00', 11, 3, 'AB4321', 3, 14, 1),
(20, '2025-11-17 15:00:00', '2025-11-17 16:30:00', 21, 4, 'AB12343', 5, 14, 1),
(21, '2025-11-17 14:00:00', '2025-11-17 15:30:00', 3, 2, 'AB12345', 3, 10, 1),
(22, '2025-11-17 12:00:00', '2025-11-17 13:05:00', 12, 3, 'AB1', 4, 10, 1),
(23, '2025-11-17 16:00:00', '2025-11-17 17:30:00', 20, 4, 'AB12343', 4, 10, 1),
(24, '2025-11-17 16:00:00', '2025-11-17 17:50:00', 19, 4, 'AB12343', 6, 10, 1),
(25, '2025-11-17 09:00:00', '2025-11-17 10:30:00', 21, 4, 'CD1418', 3, 18, 1),
(26, '2025-11-17 10:00:00', '2025-11-17 11:00:00', 11, 12, 'AB4321', 5, 18, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamiento`
--

CREATE TABLE `tratamiento` (
  `codTratam` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `detalle` varchar(60) NOT NULL,
  `productos` varchar(40) NOT NULL,
  `duracion` int(11) NOT NULL,
  `costo` double NOT NULL,
  `activo` tinyint(4) NOT NULL,
  `tipo` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tratamiento`
--

INSERT INTO `tratamiento` (`codTratam`, `nombre`, `detalle`, `productos`, `duracion`, `costo`, `activo`, `tipo`) VALUES
(1, 'Masaje Sueco', 'Terapia de relajación de cuerpo completo con presión ligera ', 'Aceite neutro', 60, 10000, 1, 'Relajacion'),
(2, 'Masaje de tejido profundo', ': Para aliviar tensión muscular crónica y tratar contractura', 'Aceite y crema neutro', 60, 12000, 1, 'Relajacion'),
(3, 'Masaje piedras calientes', ' Utiliza piedras calientes para calmar los músculos y el sis', 'Horno y piedras parafinadas', 60, 8000, 1, 'Relajacion'),
(6, 'Masajes Especializados', ' Incluyen técnicas como la aromaterapia, masajes para pareja', 'Aceites y crema neutra', 90, 15000, 0, 'Relajacion'),
(7, 'Facial básico', ' Limpieza profunda, exfoliación y mascarilla para refrescar ', 'Gua-Sha de Cuarzo Rosa, Rodillo Facial, ', 40, 9000, 1, 'Facial'),
(8, 'Faciales avanzados', ' Pueden incluir tratamientos antienvejecimiento, microdermoa', 'Rodillos de belleza, aceite de comino ne', 35, 6000, 1, 'Facial'),
(9, 'Microdermoabrasión', ' Exfolia la capa superficial de la piel usando puntas de dia', 'xfoliante Facial Microdermoabrasión Juni', 35, 6500, 1, 'Facial'),
(10, 'Radiofrecuencia', ' Usa ondas de radio para generar calor en la piel, estimulan', 'Gel Oeneis conductor específico, limpiad', 40, 7500, 1, 'Facial'),
(11, 'Ultrasonido', 'Usa ondas sonoras de alta frecuencia para limpiar la piel, p', 'Gel conductor de baja viscosidad', 30, 8000, 1, 'Facial'),
(12, 'Luz Pulsada Intensa (IPL)', ' Utilizada para fotodepilación, rejuvenecimiento de la piel ', ' Gel conductor o un gel frío, protector ', 30, 5500, 1, 'Facial'),
(13, 'Vaporizacion facial', 'Produce vapor para abrir los poros y limpiar la piel profund', 'Aceites esenciales, infusiones o hierbas', 20, 4500, 1, 'Facial'),
(14, 'Exfoliacion corporal', 'Elimina las células muertas de la piel con el uso de sales o', ' ingredientes abrasivos: sal, azúcar o p', 45, 9000, 1, 'Corporal'),
(15, 'Envolturas corporales', ' Hidratan y desintoxican la piel con ingredientes como algas', ' Algas, barro o chocolate', 60, 20000, 1, 'Corporal'),
(16, 'Hidroterapia', 'Sesiones en saunas, jacuzzis o baños de vapor.', 'Toallon', 50, 25000, 1, 'Corporal'),
(17, 'Vacumterapia', 'Es una succión corporal que mejora la circulación sanguínea ', 'Aceites a base de parafina o aceites veg', 40, 17000, 1, 'Corporal'),
(18, 'Drenaje linfático', 'Estimular la circulación del sistema linfático para eliminar', 'Crema neutra', 70, 11000, 1, 'Estetico'),
(19, ' Masaje reafirmante', ' Mejorar la firmeza y la elasticidad de la piel, a menudo en', 'Crema neutra', 80, 12000, 1, 'Estetico'),
(20, 'Masaje modelador', 'Reducir medidas, moldear la silueta y combatir la celulitis.', 'Crema neutra', 60, 12000, 1, 'Estetico'),
(21, 'Masaje Reductor', ' Utiliza técnicas firmes para romper células de grasa, mejor', 'Aceites (de almendras, coco) o cremas co', 60, 17000, 1, 'Estetico');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`codCli`) USING BTREE,
  ADD UNIQUE KEY `DNI` (`DNI`);

--
-- Indices de la tabla `consultorio`
--
ALTER TABLE `consultorio`
  ADD PRIMARY KEY (`nroConsultorio`);

--
-- Indices de la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  ADD PRIMARY KEY (`codPack`),
  ADD UNIQUE KEY `codCli` (`codCli`,`sesiones`),
  ADD KEY `sesiones` (`sesiones`);

--
-- Indices de la tabla `especialista`
--
ALTER TABLE `especialista`
  ADD PRIMARY KEY (`matricula`),
  ADD UNIQUE KEY `matricula` (`matricula`);

--
-- Indices de la tabla `instalacion`
--
ALTER TABLE `instalacion`
  ADD PRIMARY KEY (`codInstal`);

--
-- Indices de la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD PRIMARY KEY (`codSesion`),
  ADD UNIQUE KEY `codTratamiento` (`codTratamiento`,`nroConsultorio`,`matriculaMasajista`,`CodInstalacion`,`codPack`),
  ADD KEY `nroConsultorio` (`nroConsultorio`),
  ADD KEY `matriculaMasajista` (`matriculaMasajista`),
  ADD KEY `codPack` (`codPack`),
  ADD KEY `CodInstalacion` (`CodInstalacion`);

--
-- Indices de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  ADD PRIMARY KEY (`codTratam`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `codCli` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `consultorio`
--
ALTER TABLE `consultorio`
  MODIFY `nroConsultorio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  MODIFY `codPack` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `instalacion`
--
ALTER TABLE `instalacion`
  MODIFY `codInstal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `sesion`
--
ALTER TABLE `sesion`
  MODIFY `codSesion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  MODIFY `codTratam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  ADD CONSTRAINT `dia_de_spa_ibfk_1` FOREIGN KEY (`codCli`) REFERENCES `cliente` (`codCli`),
  ADD CONSTRAINT `dia_de_spa_ibfk_2` FOREIGN KEY (`sesiones`) REFERENCES `sesion` (`codSesion`);

--
-- Filtros para la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD CONSTRAINT `sesion_ibfk_1` FOREIGN KEY (`nroConsultorio`) REFERENCES `consultorio` (`nroConsultorio`),
  ADD CONSTRAINT `sesion_ibfk_2` FOREIGN KEY (`matriculaMasajista`) REFERENCES `especialista` (`matricula`),
  ADD CONSTRAINT `sesion_ibfk_3` FOREIGN KEY (`codTratamiento`) REFERENCES `tratamiento` (`codTratam`),
  ADD CONSTRAINT `sesion_ibfk_4` FOREIGN KEY (`codPack`) REFERENCES `dia_de_spa` (`codPack`),
  ADD CONSTRAINT `sesion_ibfk_5` FOREIGN KEY (`CodInstalacion`) REFERENCES `instalacion` (`codInstal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
