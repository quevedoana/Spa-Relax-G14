-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-10-2025 a las 00:43:14
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
  `Telefono` int(11) NOT NULL,
  `Edad` int(11) NOT NULL,
  `Afecciones` varchar(30) NOT NULL,
  `Estado` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`codCli`, `DNI`, `NombreCompleto`, `Telefono`, `Edad`, `Afecciones`, `Estado`) VALUES
(21, 45563392, 'Naranjo Maria Candela', 266412344, 22, 'Muy eneferma de la cabezita', 1),
(22, 44075900, 'Assat Antonio Tomas', 26645687, 23, 'Demasiado sano', 0),
(23, 39137807, 'di Fiore Mariano Enzo', 26645687, 15, 'Mucha facha', 1),
(24, 45886496, 'Barroso Esteban Jose', 26645609, 21, 'Mucho counter', 1),
(25, 43343200, 'Quevedo Ana Banana', 26647324, 25, 'Mucha lokura', 1);

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
  `sesiones` varchar(40) NOT NULL,
  `monto` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialista`
--

CREATE TABLE `especialista` (
  `matricula` varchar(10) NOT NULL,
  `NombreYApellido` varchar(60) NOT NULL,
  `telefono` int(11) NOT NULL,
  `especialidad` varchar(40) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sesion`
--

CREATE TABLE `sesion` (
  `codSesion` int(11) NOT NULL,
  `fechaYHoraInicio` datetime NOT NULL,
  `fechaYHoraFin` datetime NOT NULL,
  `codTratamiento` int(11) NOT NULL,
  `nroConsultorio` int(11) NOT NULL,
  `matriculaMasajista` varchar(10) NOT NULL,
  `CodInstalacion` int(11) NOT NULL,
  `codPack` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `activo` tinyint(1) NOT NULL,
  `tipo` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  ADD UNIQUE KEY `codCli` (`codCli`,`sesiones`);

--
-- Indices de la tabla `especialista`
--
ALTER TABLE `especialista`
  ADD PRIMARY KEY (`matricula`);

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
  MODIFY `codCli` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `consultorio`
--
ALTER TABLE `consultorio`
  MODIFY `nroConsultorio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  MODIFY `codPack` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `instalacion`
--
ALTER TABLE `instalacion`
  MODIFY `codInstal` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sesion`
--
ALTER TABLE `sesion`
  MODIFY `codSesion` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  ADD CONSTRAINT `dia_de_spa_ibfk_1` FOREIGN KEY (`codCli`) REFERENCES `cliente` (`codCli`);

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
