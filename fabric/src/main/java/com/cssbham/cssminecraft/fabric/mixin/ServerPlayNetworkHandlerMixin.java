package com.cssbham.cssminecraft.fabric.mixin;

import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import com.cssbham.cssminecraft.fabric.listener.FabricEventAdapter;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "handleMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Ljava/util/function/Function;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
	private void onHandleMessage(TextStream.Message message, CallbackInfo ci) {
		if (null == player) {
			return;
		}

		String name = player.getName().getString();
		FabricEventAdapter.dispatchEvent(FabricEventAdapter.EVENT_BUS, new ServerMessageEvent(
				player.getUuid(),
				player.getName().getString(),
				(null == player.getDisplayName()) ? name : player.getDisplayName().getString(),
				message.getRaw()
		));
	}

}
