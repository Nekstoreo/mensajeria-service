# Documentación Técnica: Microservicio de Mensajería

Este documento detalla la arquitectura, diseño e implementación del microservicio de mensajería (`mensajeria-service`). Este componente es responsable de gestionar las notificaciones del sistema, específicamente el envío de mensajes SMS a los clientes para informar sobre el estado de sus pedidos.

## Arquitectura

El servicio implementa una **Arquitectura Hexagonal (Puertos y Adaptadores)**, lo que garantiza el desacoplamiento entre la lógica de negocio y las implementaciones tecnológicas externas como proveedores de mensajería o frameworks web.

La estructura se organiza en tres capas concéntricas:

1.  **Dominio (Domain)**: Lógica de negocio y reglas del sistema.
2.  **Aplicación (Application)**: Orquestación de casos de uso y transformación de datos.
3.  **Infraestructura (Infrastructure)**: Implementación de adaptadores y configuración técnica.

---

## 1. Capa de Dominio (Domain)

El núcleo del servicio, donde residen las entidades y la lógica pura de notificación, sin dependencias de frameworks externos.

### Modelos
Objetos de negocio que representan la información esencial para el proceso de mensajería:
*   **NotificationMessage**: Encapsula los datos necesarios para enviar una notificación (número de teléfono, contenido del mensaje).
*   **NotificationResult**: Representa el resultado de la operación de envío (estado de éxito/fallo, identificador de transacción).

### Puertos (Ports)
Interfaces que definen los contratos de interacción del dominio con el mundo exterior.
*   **API (Inbound Ports)**: Definen los servicios que el dominio ofrece a la capa de aplicación.
    *   `INotificationServicePort`: Contrato para solicitar el envío de notificaciones.
*   **SPI (Outbound Ports)**: Contratos que el dominio requiere que sean implementados por la infraestructura.
    *   `ISmsMessagingPort`: Interfaz para el proveedor de servicios de SMS. Permite cambiar de proveedor (ej. Twilio, AWS SNS) sin afectar la lógica de negocio.

### Casos de Uso (Use Cases)
Implementación concreta de la lógica de negocio definida en los puertos API.
*   **NotificationUseCase**: Orquesta el proceso de envío. Recibe la solicitud, valida los datos y delega la entrega real al puerto de salida (`ISmsMessagingPort`).

---

## 2. Capa de Aplicación (Application)

Capa encargada de coordinar la interacción entre la infraestructura (controladores REST) y el dominio.

### Handlers
Componentes que actúan como intermediarios y orquestadores.
*   **NotificationHandler**: Recibe las peticiones desde los controladores, invoca a los mappers para convertir DTOs a modelos de dominio y ejecuta el caso de uso `NotificationUseCase`.

### DTOs y Mappers
*   **DTOs (Data Transfer Objects)**: Objetos diseñados para la transferencia de datos a través de la red, desacoplando la API REST del modelo interno.
    *   `OrderReadyNotificationRequestDto`: Estructura de la petición entrante (ej. número de teléfono, mensaje).
    *   `NotificationResponseDto`: Estructura de la respuesta al cliente.
*   **Mappers**: Utilización de **MapStruct** (`NotificationDtoMapper`) para realizar la conversión automática y segura entre DTOs y los modelos de dominio (`NotificationMessage`).

---

## 3. Capa de Infraestructura (Infrastructure)

Capa más externa que contiene las implementaciones técnicas y la configuración del framework.

### Input (Driving Adapters)
Adaptadores que inician la interacción con el sistema.
*   **Rest Controllers**: `NotificationRestController`. Expone endpoints REST para que otros microservicios (como `plazoleta-service`) puedan solicitar el envío de notificaciones. Maneja la validación de entrada y delega el procesamiento al `NotificationHandler`.

### Output (Driven Adapters)
Implementaciones concretas de los puertos de salida (SPI).
*   **Twilio Adapter**: Implementación de `ISmsMessagingPort` utilizando la API de **Twilio**.
    *   `TwilioSmsAdapter`: Clase que adapta la interfaz del dominio a la librería cliente de Twilio.
    *   `TwilioSmsClient` y `TwilioConfiguration`: Componentes auxiliares para gestionar la conexión y credenciales con el servicio de terceros.

### Configuración y Seguridad
*   **BeanConfiguration**: Clase de configuración de Spring que realiza la inyección de dependencias manual para los componentes del dominio. Instancia `NotificationUseCase` inyectando la implementación concreta del adaptador de Twilio. Esto asegura que el dominio permanezca agnóstico al framework de inyección de dependencias.
*   **Security**: Implementación de seguridad mediante JWT.
    *   `JwtAuthenticationFilter` y `JwtTokenValidator`: Interceptan las peticiones HTTP para validar el token de autorización, asegurando que solo servicios o usuarios autorizados puedan disparar notificaciones.

## Decisiones de Diseño Relevantes

1.  **Abstracción del Proveedor de SMS**: Se definió la interfaz `ISmsMessagingPort` para no acoplar el sistema a Twilio. Esto permite, en el futuro, cambiar a otro proveedor de mensajería o implementar un "mock" para pruebas simplemente cambiando la implementación inyectada en `BeanConfiguration`.
2.  **Arquitectura Hexagonal**: La estricta separación de capas facilita las pruebas unitarias del dominio, ya que se puede probar `NotificationUseCase` aislando la dependencia de Twilio mediante mocks del puerto `ISmsMessagingPort`.
3.  **Manejo de Excepciones Global**: Uso de `GlobalExceptionHandler` en la infraestructura para capturar excepciones (como fallos en el envío o números inválidos) y retornar respuestas HTTP estandarizadas y limpias.
4.  **Inyección de Dependencias Manual**: Se optó por configurar los beans del dominio manualmente en lugar de usar anotaciones de Spring (`@Service`, `@Autowired`) dentro de las clases de dominio, preservando la pureza de la arquitectura y evitando la contaminación del núcleo de negocio con dependencias del framework.
