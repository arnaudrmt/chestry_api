# ChestryAPI 🚀

<br/>

**Note: This project is currently under development and should not be shared with anyone else except for authorized personnel. It is a confidential project.**

**ChestryAPI** is a custom all-in-one solution designed to enable the seamless connection between a BungeeCord server, a Spigot server, Docker, MongoDB, and the OVH API. It is being developed specifically for a Minecraft server hosting website, providing a comprehensive set of features to enhance the server ordering and management process.

<br/>

## Key Features 🌟

- **Server Ordering and Management**: ChestryAPI offers a user-friendly menu upon entering the server. From the menu, users can easily access their current servers, place new server orders, and manage their accounts. This streamlined process simplifies server management and enhances the user experience.

- **Flexible Server Options**: When ordering a server, users are presented with a list of options such as 'Vanilla,' 'Spigot,' and 'Modpack.' Upon selecting the server type, they can then choose from a list of supported versions. All available versions and options are stored in MongoDB and retrieved during the Spigot server's initial launch.

- **Custom Payment Links**: Users can order servers using Stripe-implemented custom payment links. This secure payment system ensures a seamless and reliable transaction process when purchasing server services.

- **BungeeCord Integration**: ChestryAPI utilizes Plugin Messages to communicate with BungeeCord. Once a player selects and orders a server, the corresponding server configuration information is sent via Plugin Message to BungeeCord. This allows BungeeCord to create a container with the requested server and store server information in MongoDB for easy tracking and management.

- **Custom Domain Support**: Users have the option to order a custom domain for their servers. BungeeCord sends a request to the OVH API to create a DNS entry, generating a subdomain like 'myserver.example.com' for direct server access. This feature enhances server personalization and accessibility.

- **Simplified Development**: The Spigot and BungeeCord plugins are consolidated into a single file. This architectural decision streamlines the development process, making it easier to manage and maintain the codebase.

<br/>

## Usage 📝

ChestryAPI is designed specifically for integration with a Minecraft server hosting website. Proper configuration and setup are required to ensure compatibility with your specific infrastructure and environment. Detailed documentation and guidelines will be provided for seamless integration and utilization of ChestryAPI's features.

**Note: As ChestryAPI is a confidential project currently under development, access and usage are limited to authorized personnel only.**

<br/>

## Contributing 🤝

Contributions to ChestryAPI are not open to the public at this time due to its confidential nature. The repository serves as an example of how ChestryAPI can be utilized to enhance server ordering and management processes.

<br/>

## Contact ✉️

If you have any questions or inquiries regarding the project, please reach out to the authorized personnel directly.

Thank you for your understanding and cooperation in maintaining the confidentiality of ChestryAPI.
