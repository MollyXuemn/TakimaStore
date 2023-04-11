// src/models/article.ts
export interface Article {
  id: number;
  price: string;
  seller: { id: number; name: string };
  product: {
    "@type": string;
    id: number;
    ref: string;
    name: string;
    brand: string | null;
    description: string;
    image: string;
    basePrice: string;
  };
}
