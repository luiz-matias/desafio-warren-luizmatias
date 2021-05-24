package br.com.warren.challenge.app.exceptions

class InvalidEmailException : Exception("Invalid e-mail format!")
class InvalidPasswordException : Exception("Invalid password!")
class UnauthorizedException : Exception("Your access token is no longer valid. Reauth needed.")
class HttpException : Exception("HTTP request isn't between OK range (2XX..3XX)")