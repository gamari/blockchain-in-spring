import { ChainType, WalletInformation } from "../types/blockchain";

/**
 * ウォレット情報の取得。
 * 
 * @param fetch_url APIのURL
 * @returns 
 */
export const fetch_wallet = async (fetch_url: string): Promise<WalletInformation> => {
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

export const fetch_chain = async (fetch_url: string): Promise<ChainType> => {
    const response = await fetch(fetch_url, {
        method: "GET",
        mode: "cors",
        headers: {
            "Content-Type": "application/json",
        },
    });
    const json = await response.json();
    const ret: ChainType = {
        blocks: json["chain"]
    };

    return ret;
}

export const get_amount = async (address: string, fetch_url: string): Promise<string> => {
    const params = {
        address: address,
    };
    const query_params = new URLSearchParams(params);
    const amount_url = `${fetch_url}?${query_params}`

    const response = await fetch(amount_url, {
        method: "GET",
    });

    const json = await response.json();

    const ret = json["amount"]
    return ret;
}