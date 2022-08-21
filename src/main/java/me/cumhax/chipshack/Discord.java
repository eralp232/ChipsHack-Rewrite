package me.cumhax.chipshack;

import net.minecraft.client.Minecraft;

import net.minecraft.client.multiplayer.ServerData;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class Discord {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static String details;
    public static String state;
    public static int players;
    public static int maxPlayers;
    public static int players2;
    public static int maxPlayers2;
    public static ServerData svr;
    public static String[] popInfo;

    public static void start() {

        String applicationId = "931601942461956136";
        String steamId = "";

        DiscordRichPresence presence = new DiscordRichPresence();
        DiscordEventHandlers handlers = new DiscordEventHandlers();

        DiscordRPC.INSTANCE.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRPC.INSTANCE.Discord_UpdatePresence(presence);

        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.details = "Vibin";
        presence.state = "Chipshack";
        presence.largeImageKey = "he";
        presence.largeImageText = "Rewrite";

        new Thread(() ->
        {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = "";
                    state = "";
                    players = 0;
                    maxPlayers = 0;
                    if (mc.isIntegratedServerRunning()) {
                        details = "on his tod";
                    }
                    else if (mc.getCurrentServerData() != null) {
                        svr = mc.getCurrentServerData();
                        if (!svr.serverIP.equals("")) {
                            details = "ballin";
                            state = svr.serverIP;
                            if (svr.populationInfo != null) {
                                popInfo = svr.populationInfo.split("/");
                                if (popInfo.length > 2) {
                                    players2 = Integer.parseInt(popInfo[0]);
                                    maxPlayers2 = Integer.parseInt(popInfo[1]);
                                }
                            }
                        }
                    }
                    else {
                        details = "Vibin";
                        state = "masturbating";
                    }
                    if (!details.equals(presence.details) || !state.equals(presence.state)) {
                        presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    presence.details = details;
                    presence.state = state;
                    DiscordRPC.INSTANCE.Discord_UpdatePresence(presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void stop() {
        DiscordRPC.INSTANCE.Discord_Shutdown();
        DiscordRPC.INSTANCE.Discord_ClearPresence();
    }

}
