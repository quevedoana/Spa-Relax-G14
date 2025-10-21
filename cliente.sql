-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-10-2025 a las 21:30:18
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
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `codCli` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
