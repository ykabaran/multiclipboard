# multiclipboard
A manager tool that simulates multiple clipboards with keyboard shortcuts

The project uses the jnativehook library to hook into global keypresses. Shortcut combination Ctrl + Alt + C primes the application for input. After priming pressing any digit key 0-9 switches the clipboard into the slot. The application starts with Clipboard #1, and show a notificaiton when the clipboard slot is changed.

Note:
I am aware that my private maven repo is used in this project for logging utility classes. I will remove the dependency soon.
