export interface User {
  email: string;
  password: string;
  captchaResponse: string | undefined;
  name: string;
  phoneNumber: string;
}
