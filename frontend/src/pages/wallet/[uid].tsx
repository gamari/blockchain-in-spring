import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { blockchain_atom } from "../../atoms/BlockChainAtoms";
import { wallet_amount_atom } from "../../atoms/WalletAtoms";
import ActionBox from "../../components/ActionBox";
import BlockChainInformation from "../../components/BlockChainInformation";
import Footer from "../../components/Footer";
import SendMoney from "../../components/SendMoney";
import WalletInformation from "../../components/WalletInformation";
import { chain_url, wallet_url } from "../../constants/ulrs";
import { fetch_chain, fetch_wallet } from "../../libs/FetchApi";
import { UserType } from "../../types/user";

const uid = () => {
  // Wallet information
  const [privateKey, setPrivateKey] = useState<string>("");
  const [publicKey, setPublicKey] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [amount, setAmount] = useRecoilState(wallet_amount_atom);

  // Chain Information
  const [chain, setChain] = useRecoilState(blockchain_atom);
  const router = useRouter();
  const { uid } = router.query;

  useEffect(() => {
    if (router.isReady) {
      handleGetWallet();
      handleGetChain();
    }
  }, [uid, router]);

  const handleGetWallet = async () => {
    // TODO リファクタリング
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const { uid } = router.query;

    const user: UserType = users.filter((user: UserType) => {
      return user?.uid == uid;
    })[0];

    if (
      user &&
      user?.uid &&
      user?.private_key &&
      user?.public_key &&
      user?.address
    ) {
      setPublicKey(user?.public_key);
      setAddress(user?.address);
      setPrivateKey(user?.private_key);
      return;
    }

    // ウォレット情報の取得
    const wallet = await fetch_wallet(wallet_url);

    setPublicKey(wallet.public_key);
    setAddress(wallet.wallet_address);
    setPrivateKey(wallet.private_key);
    setAmount(wallet.amount);

    const updateUsers = users?.map((user: UserType) => {
      if (user?.uid == uid) {
        user.address = wallet.wallet_address;
        user.private_key = wallet.private_key;
        user.public_key = wallet.public_key;
      }
      console.log(user);
      return user;
    });
    updateUsers && localStorage.setItem("users", JSON.stringify(updateUsers));
  };

  const handleGetChain = async () => {
    const chain = await fetch_chain(chain_url);
    setChain(chain);
  };

  return (
    <div className="section">
      <WalletInformation
        publicKey={publicKey}
        privateKey={privateKey}
        address={address}
        amount={amount}
      />
      <SendMoney
        publicKey={publicKey}
        privateKey={privateKey}
        address={address}
      />
      <ActionBox address={address} />
      <BlockChainInformation chain={chain} />

      <Footer />
    </div>
  );
};

export default uid;
