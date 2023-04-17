import axios from "axios";
import { Customer } from "../customer";

export async function getCustomer(userId: number) {
  return axios.get<Customer>(`${Config.apiBaseUrl}/customers/${userId}`);
}
