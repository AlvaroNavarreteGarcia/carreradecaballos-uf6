-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-01-2025 a las 10:45:49
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `carrera_de_caballos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `apuestas`
--

CREATE TABLE `apuestas` (
  `id` int(11) NOT NULL,
  `jugador_id` int(11) NOT NULL,
  `caballo_id` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caballos`
--

CREATE TABLE `caballos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `velocidad` decimal(5,2) NOT NULL,
  `resistencia` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_carrera`
--

CREATE TABLE `estado_carrera` (
  `id` int(11) NOT NULL,
  `turno` int(11) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `jugadores` text DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partidas`
--

CREATE TABLE `partidas` (
  `id_partida` int(11) NOT NULL,
  `ganador` varchar(255) DEFAULT NULL,
  `caballo_ganador` varchar(255) DEFAULT NULL,
  `bote` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `partidas`
--

INSERT INTO `partidas` (`id_partida`, `ganador`, `caballo_ganador`, `bote`) VALUES
(1, 'alvaro', 'Caballo de Espadas', 62),
(2, 'pedro', 'Juanito', 100),
(3, 'alvaro', 'vector', 100),
(4, 'paula', 'koi', 100),
(5, 'pedro', 'bonita', 100),
(6, 'Alvaro', 'ramoncín', 100),
(7, 'Alvaro', 'ramoncin', 100),
(8, 'alvaro', 'ramoncin', 100);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `apuestas`
--
ALTER TABLE `apuestas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `caballo_id` (`caballo_id`);

--
-- Indices de la tabla `caballos`
--
ALTER TABLE `caballos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `estado_carrera`
--
ALTER TABLE `estado_carrera`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `partidas`
--
ALTER TABLE `partidas`
  ADD PRIMARY KEY (`id_partida`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `apuestas`
--
ALTER TABLE `apuestas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `caballos`
--
ALTER TABLE `caballos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `estado_carrera`
--
ALTER TABLE `estado_carrera`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `partidas`
--
ALTER TABLE `partidas`
  MODIFY `id_partida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `apuestas`
--
ALTER TABLE `apuestas`
  ADD CONSTRAINT `apuestas_ibfk_1` FOREIGN KEY (`caballo_id`) REFERENCES `caballos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
