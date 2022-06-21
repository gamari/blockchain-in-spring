import { WalletInformation } from "../types/blockchain";

/**
 * 
 * @param fetch_url APIのURL
 * @returns 
 */
export async function fetch_chain(fetch_url: string): Promise<WalletInformation> {
    const response = await fetch(fetch_url, {
        method: "POST",
        mode: "cors",
        headers: {
            "Content-Type": "application/json",
        },
    })

    const json = await response.json();

    const wallet_information: WalletInformation = {
        private_key: json["privateKey"],
        public_key: json["publicKey"],
        wallet_address: json["blockchainAddress"],
        amount: json["amount"],
    }

    return wallet_information;
}

