import { Client } from "./Client";

export interface Account {
    id: string;
    nome: string;
    cliente: Client;
}