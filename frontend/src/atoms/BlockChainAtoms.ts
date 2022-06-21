import { atom } from "recoil";
import { ChainType } from "../types/blockchain";

export const blockchain_atom = atom<ChainType>({
    key: 'blockchainAtom',
    default: undefined,
});
