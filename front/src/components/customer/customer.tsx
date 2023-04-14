// src/models/article.ts
export interface Customer {
  id: number;
  gender: string;
  firstName: string;
  lastName: string;
  email: string;

  address: {
    street: string;
    city: string;
    zipcode: string;
    country: string;
  };
  iban: string | null;
}
